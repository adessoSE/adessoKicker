package de.adesso.kicker.notification.matchverificationrequest.service;

import de.adesso.kicker.events.match.MatchCreatedEvent;
import de.adesso.kicker.events.match.MatchDeclinedEvent;
import de.adesso.kicker.events.match.MatchVerificationSentEvent;
import de.adesso.kicker.events.match.MatchVerifiedEvent;
import de.adesso.kicker.match.persistence.Match;
import de.adesso.kicker.notification.matchverificationrequest.persistence.MatchVerificationRequest;
import de.adesso.kicker.notification.matchverificationrequest.persistence.MatchVerificationRequestRepository;
import de.adesso.kicker.user.persistence.User;
import de.adesso.kicker.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class VerifyMatchService {

    private final MatchVerificationRequestRepository matchVerificationRequestRepository;

    private final UserService userService;

    private final ApplicationEventPublisher applicationEventPublisher;

    public void acceptRequest(MatchVerificationRequest matchVerificationRequest) {
        sendMatchVerifiedEvent(matchVerificationRequest.getMatch());
        List<MatchVerificationRequest> requests = getRequestsByMatch(matchVerificationRequest.getMatch());
        for (MatchVerificationRequest request : requests) {
            deleteRequest(request);
        }
    }

    @EventListener
    public void sendRequests(MatchCreatedEvent matchCreatedEvent) {
        Match match = matchCreatedEvent.getMatch();
        User sender = userService.getLoggedInUser();
        List<User> receivers = new ArrayList<>();

        if (match.getWinners().contains(sender)) {
            receivers.addAll(match.getLosers());
        } else {
            receivers.addAll(match.getWinners());
        }
        for (User receiver : receivers) {
            MatchVerificationRequest request = new MatchVerificationRequest(sender, receiver, match);
            saveRequest(request);
            sendMatchVerificationRequestEvent(request);
        }
    }

    public List<User> declineRequest(MatchVerificationRequest matchVerificationRequest) {
        deleteRequest(matchVerificationRequest);
        sendMatchRequestDeclinedEvent(matchVerificationRequest.getMatch());
        List<MatchVerificationRequest> otherRequests = getRequestsByMatch(matchVerificationRequest.getMatch());
        List<User> usersToInform = new ArrayList<>();

        for (MatchVerificationRequest request : otherRequests) {
            deleteRequest(request);
        }
        for (User player : matchVerificationRequest.getMatch().getPlayers()) {
            if (!userService.getLoggedInUser().equals(player)) {
                usersToInform.add(player);
            }
        }
        return usersToInform;
    }

    private void sendMatchVerificationRequestEvent(MatchVerificationRequest matchVerificationRequest) {
        MatchVerificationSentEvent matchVerificationSentEvent = new MatchVerificationSentEvent(this,
                matchVerificationRequest);
        applicationEventPublisher.publishEvent(matchVerificationSentEvent);
    }

    private void sendMatchVerifiedEvent(Match match) {
        MatchVerifiedEvent matchVerifiedEvent = new MatchVerifiedEvent(this, match);
        applicationEventPublisher.publishEvent(matchVerifiedEvent);
    }

    private void sendMatchRequestDeclinedEvent(Match match) {
        MatchDeclinedEvent matchDeclinedEvent = new MatchDeclinedEvent(this, match);
        applicationEventPublisher.publishEvent(matchDeclinedEvent);
    }

    private List<MatchVerificationRequest> getRequestsByMatch(Match match) {
        return matchVerificationRequestRepository.getAllByMatch(match);
    }

    private void deleteRequest(MatchVerificationRequest matchVerificationRequest) {
        matchVerificationRequestRepository.delete(matchVerificationRequest);
    }

    private void saveRequest(MatchVerificationRequest matchVerificationRequest) {
        matchVerificationRequestRepository.save(matchVerificationRequest);
    }
}
