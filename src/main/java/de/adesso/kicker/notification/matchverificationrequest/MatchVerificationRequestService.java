package de.adesso.kicker.notification.matchverificationrequest;

import de.adesso.kicker.match.Match;
import de.adesso.kicker.team.Team;
import de.adesso.kicker.tournament.Tournament;
import de.adesso.kicker.tournament.singleelimination.SingleElimination;
import de.adesso.kicker.tournament.singleelimination.SingleEliminationService;
import de.adesso.kicker.user.User;
import de.adesso.kicker.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchVerificationRequestService {

    private UserService userService;
    private MatchVerificationRequestRepository matchVerificationRequestRepository;
    private SingleEliminationService singleEliminationService;

    @Autowired
    public MatchVerificationRequestService(UserService userService,
            MatchVerificationRequestRepository matchVerificationRequestRepository,
            SingleEliminationService singleEliminationService) {

        this.userService = userService;
        this.matchVerificationRequestRepository = matchVerificationRequestRepository;
        this.singleEliminationService = singleEliminationService;
    }

    public void acceptMatchVerificationRequest(long notificationId) {

        MatchVerificationRequest matchVerificationRequest = matchVerificationRequestRepository
                .findByNotificationId(notificationId);

        if (matchVerificationRequest == null) {
            System.err.println(
                    "ERROR at 'MatchVerificationRequestService' --> 'acceptMatchVerificationRequest()' : cannot find MatchVerificationRequest with id: "
                            + notificationId);
            return;
        }
        matchVerificationRequest.getMatch().setWinner(matchVerificationRequest.getWinner());

        if (matchVerificationRequest.getTournament() instanceof SingleElimination) {
            singleEliminationService.advanceWinner((SingleElimination) matchVerificationRequest.getTournament(),
                    matchVerificationRequest.getMatch());
        }
        matchVerificationRequestRepository.deleteByMatch(matchVerificationRequest.getMatch());
    }

    public void declineMatchVerificationRequest(long notificationId) {

        MatchVerificationRequest matchVerificationRequest = matchVerificationRequestRepository
                .findByNotificationId(notificationId);

        if (matchVerificationRequest == null) {
            System.err.println(
                    "ERROR at 'MatchVerificationRequestService' --> 'declineMatchVerificationRequest()' : cannot find MatchVerificationRequest with id: "
                            + notificationId);
            return;
        }
        User decliner = userService.getLoggedInUser();
        matchVerificationRequestRepository.deleteByMatch(matchVerificationRequest.getMatch());
        // Circular dependency :(
        // notificationService.createNotification(decliner.getUserId(),
        // matchVerificationRequest.getSender().getUserId(), decliner.getFirstName() + "
        // " + decliner.getLastName() + " declined your request to set team " +
        // matchVerificationRequest.getWinner().getTeamName() + " as the winner of the
        // match from " + matchVerificationRequest.getMatch().getGermanDate());

    }

    public void generateMatchVerificationRequests(Match match, Team winner, Tournament tournament) {

        User sender = userService.getLoggedInUser();
        Team receiverTeam;

        if (matchVerificationRequestRepository.findByMatch(match) != null) {
            System.err.println(
                    "ERROR at 'MatchVerificationRequestService' --> 'generateMatchVerificationRequests()' : requests for this match already send");
            return;
        }

        if (sender == winner.getPlayerB() || sender == winner.getPlayerA()) {
            receiverTeam = (match.getTeamA() == winner ? match.getTeamB() : match.getTeamA());
        } else {
            receiverTeam = (match.getTeamA() == winner ? match.getTeamA() : match.getTeamB());
        }
        saveMatchVerificationRequest(sender, receiverTeam.getPlayerA(), match, winner, tournament);
        saveMatchVerificationRequest(sender, receiverTeam.getPlayerB(), match, winner, tournament);
    }

    public MatchVerificationRequest createMatchVerificationRequest(User sender, User receiver, Match match, Team winner,
            Tournament tournament) {

        if (sender == null) {
            System.err.println(
                    "ERROR at 'MatchVerificationRequestService' --> 'createMatchVerificationRequest()' : sender is NULL ");
            return null;
        }
        if (winner == null) {
            System.err.println(
                    "ERROR at 'MatchVerificationRequestService' --> 'createMatchVerificationRequest()' : winner team is NULL ");
            return null;
        }
        return new MatchVerificationRequest(sender, receiver, match, winner, tournament);
    }

    public void saveMatchVerificationRequest(User sender, User receiver, Match match, Team winner,
            Tournament tournament) {

        saveMatchVerificationRequest(createMatchVerificationRequest(sender, receiver, match, winner, tournament));
    }

    public void saveMatchVerificationRequest(MatchVerificationRequest matchVerificationRequest) {

        matchVerificationRequestRepository.save(matchVerificationRequest);
    }
}
