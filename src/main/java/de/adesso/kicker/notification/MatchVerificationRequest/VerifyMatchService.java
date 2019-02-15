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

        matchVerificationRequest.getMatch().setVerified(true);
        // TODO execute ranking algorithm
        List<MatchVerificationRequest> requests = matchVerificationRequestRepository
                .getAllByMatch(matchVerificationRequest.getMatch());
        for (MatchVerificationRequest request : requests) {
            matchVerificationRequestRepository.delete(request);
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
            matchVerificationRequestRepository.save(request);
        }
    }

    public List<User> declineRequest(MatchVerificationRequest matchVerificationRequest) {

        deleteRequest(matchVerificationRequest);
        List<MatchVerificationRequest> otherRequests = matchVerificationRequestRepository
                .getAllByMatch(matchVerificationRequest.getMatch());
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

    public void deleteRequest(MatchVerificationRequest matchVerificationRequest) {

        matchVerificationRequestRepository.delete(matchVerificationRequest);
    }
}
