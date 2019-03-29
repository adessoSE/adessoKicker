package de.adesso.kicker.notification.matchverificationrequest.service;

import de.adesso.kicker.match.persistence.Match;
import de.adesso.kicker.match.service.events.MatchCreatedEvent;
import de.adesso.kicker.match.service.events.MatchDeclinedEvent;
import de.adesso.kicker.match.service.events.MatchVerifiedEvent;
import de.adesso.kicker.notification.matchverificationrequest.persistence.MatchVerificationRequest;
import de.adesso.kicker.notification.matchverificationrequest.persistence.MatchVerificationRequestRepository;
import de.adesso.kicker.notification.matchverificationrequest.service.events.MatchVerificationSentEvent;
import de.adesso.kicker.user.persistence.User;
import de.adesso.kicker.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class VerifyMatchService {

    private final MatchVerificationRequestRepository matchVerificationRequestRepository;

    private final UserService userService;

    private final ApplicationEventPublisher applicationEventPublisher;

    public void acceptRequest(MatchVerificationRequest matchVerificationRequest) {
        sendMatchVerifiedEvent(matchVerificationRequest.getMatch());
        var requests = getRequestsByMatch(matchVerificationRequest.getMatch());
        deleteRequests(requests);
    }

    @EventListener
    public void sendRequests(MatchCreatedEvent matchCreatedEvent) {
        var match = matchCreatedEvent.getMatch();
        var sender = match.getTeamAPlayer1();
        var receivers = getReceivers(match, sender);
        receivers.forEach(receiver -> createAndSaveRequest(sender, receiver, match));
    }

    private List<User> getReceivers(Match match, User sender) {
        var winners = match.getWinners();
        var losers = match.getLosers();
        if (winners.contains(sender)) {
            return losers;
        } else {
            return winners;
        }
    }

    private void createAndSaveRequest(User sender, User receiver, Match match) {
        var request = new MatchVerificationRequest(sender, receiver, match);
        saveRequest(request);
        sendMatchVerificationRequestEvent(request);
    }

    public List<User> declineRequest(MatchVerificationRequest matchVerificationRequest) {
        deleteRequest(matchVerificationRequest);

        var remainingRequests = getRequestsByMatch(matchVerificationRequest.getMatch());
        deleteRequests(remainingRequests);

        var match = matchVerificationRequest.getMatch();
        var players = match.getPlayers();
        var currentUser = userService.getLoggedInUser();
        sendMatchRequestDeclinedEvent(match);
        return players.stream().filter(user -> !user.equals(currentUser)).collect(Collectors.toList());
    }

    private void sendMatchVerificationRequestEvent(MatchVerificationRequest matchVerificationRequest) {
        var matchVerificationSentEvent = new MatchVerificationSentEvent(this, matchVerificationRequest);
        applicationEventPublisher.publishEvent(matchVerificationSentEvent);
    }

    private void sendMatchVerifiedEvent(Match match) {
        var matchVerifiedEvent = new MatchVerifiedEvent(this, match);
        applicationEventPublisher.publishEvent(matchVerifiedEvent);
    }

    private void sendMatchRequestDeclinedEvent(Match match) {
        var matchDeclinedEvent = new MatchDeclinedEvent(this, match);
        applicationEventPublisher.publishEvent(matchDeclinedEvent);
    }

    private List<MatchVerificationRequest> getRequestsByMatch(Match match) {
        return matchVerificationRequestRepository.getAllByMatch(match);
    }

    private void deleteRequests(List<MatchVerificationRequest> matchVerificationRequests) {
        matchVerificationRequestRepository.deleteAll(matchVerificationRequests);
    }

    private void deleteRequest(MatchVerificationRequest matchVerificationRequest) {
        matchVerificationRequestRepository.delete(matchVerificationRequest);
    }

    private void saveRequest(MatchVerificationRequest matchVerificationRequest) {
        matchVerificationRequestRepository.save(matchVerificationRequest);
    }
}
