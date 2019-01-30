package de.adesso.kicker.notification.tournamentjoinrequest;

import de.adesso.kicker.notification.NotificationRepository;
import de.adesso.kicker.team.Team;
import de.adesso.kicker.tournament.Tournament;
import de.adesso.kicker.tournament.singleelimination.SingleElimination;
import de.adesso.kicker.tournament.singleelimination.SingleEliminationService;
import de.adesso.kicker.user.User;
import de.adesso.kicker.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TournamentJoinRequestService {

    private NotificationRepository notificationRepository;
    private UserService userService;
    private SingleEliminationService singleEliminationService;

    @Autowired
    public TournamentJoinRequestService(NotificationRepository notificationRepository, UserService userService,
            SingleEliminationService singleEliminationService) {

        this.notificationRepository = notificationRepository;
        this.userService = userService;
        this.singleEliminationService = singleEliminationService;
    }

    public void acceptTournamentJoinRequest(long notificationId) {

        if (!notificationRepository.existsById(notificationId)) {
            System.err.println(
                    "ERROR at 'TournamentJoinRequestService' --> 'acceptTournamentJoinRequest()' : Cannot find TournamentJoinRequest with id "
                            + notificationId);
            return;
        }
        TournamentJoinRequest request = (TournamentJoinRequest) notificationRepository
                .findByNotificationId(notificationId);
        // Can only be done here! Otherwise it would cause a circular dependency
        // (SingularEliminationService <--> TournamentJoinRequestService)
        singleEliminationService.addTeamToTournament((SingleElimination) request.getTargetTournament(),
                request.getTargetTeam());
        singleEliminationService.addPlayer((SingleElimination) request.getTargetTournament(),
                request.getTargetTeam().getPlayerA());
        singleEliminationService.addPlayer((SingleElimination) request.getTargetTournament(),
                request.getTargetTeam().getPlayerB());
        singleEliminationService.saveTournament((SingleElimination) request.getTargetTournament());
    }

    public TournamentJoinRequest createTournamentJoinRequest(long senderId, Team team, Tournament tournament) {

        User sender = userService.getUserById(senderId);
        User receiver;

        // Validation
        if (sender == null) {
            System.err.println(
                    "ERROR at 'TournamentJoinRequestService' --> 'createTournamentJoinRequest()' : Cannot find sender with id "
                            + senderId);
            return null;
        }
        if (team == null) {
            System.err.println(
                    "ERROR at 'TournamentJoinRequestService' --> 'createTournamentJoinRequest()' : Team is NULL ");
            return null;
        }
        if (tournament == null) {
            System.err.println(
                    "ERROR at 'TournamentJoinRequestService' --> 'createTournamentJoinRequest()' : Tournament is NULL ");
            return null;
        }

        if (team.getPlayerA() == userService.getUserById(senderId)) {
            receiver = team.getPlayerB();
        } else {
            receiver = team.getPlayerA();
        }
        return new TournamentJoinRequest(sender, receiver, team, tournament);
    }

    public void saveTournamentJoinRequest(TournamentJoinRequest tournamentJoinRequest) {

        if (tournamentJoinRequest == null) {
            System.err.println(
                    "ERROR at 'TournamentService' --> 'saveTournamentJoinRequest' : Given TournamentJoinRequest is null");
            return;
        }
        notificationRepository.save(tournamentJoinRequest);
    }

    public void saveTournamentJoinRequest(long senderId, Team team, Tournament tournament) {

        saveTournamentJoinRequest(createTournamentJoinRequest(senderId, team, tournament));
    }
}
