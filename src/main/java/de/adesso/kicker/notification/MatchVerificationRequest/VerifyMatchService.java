package de.adesso.kicker.notification.MatchVerificationRequest;

import de.adesso.kicker.match.Match;
import de.adesso.kicker.user.User;
import de.adesso.kicker.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VerifyMatchService {

    private MatchVerificationRequestRepository matchVerificationRequestRepository;

    private UserService userService;

    @Autowired
    public VerifyMatchService(MatchVerificationRequestRepository matchVerificationRequestRepository,
            UserService userService) {
        this.matchVerificationRequestRepository = matchVerificationRequestRepository;
        this.userService = userService;
    }

    public void acceptRequest(MatchVerificationRequest matchVerificationRequest) {
        // TODO trigger event to verify match
        // TODO execute ranking algorithm
        List<MatchVerificationRequest> requests = getRequestsByMatch(matchVerificationRequest.getMatch());
        for (MatchVerificationRequest request : requests) {
            deleteRequest(request);
        }
    }

    public void sendRequests(Match match) {
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
        }
    }

    public List<User> declineRequest(MatchVerificationRequest matchVerificationRequest) {
        // TODO delete match
        deleteRequest(matchVerificationRequest);
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

    public List<MatchVerificationRequest> getRequestsByMatch(Match match) {
        return matchVerificationRequestRepository.getAllByMatch(match);
    }

    public void deleteRequest(MatchVerificationRequest matchVerificationRequest) {
        matchVerificationRequestRepository.delete(matchVerificationRequest);
    }

    public void saveRequest(MatchVerificationRequest matchVerificationRequest) {
        matchVerificationRequestRepository.save(matchVerificationRequest);
    }
}
