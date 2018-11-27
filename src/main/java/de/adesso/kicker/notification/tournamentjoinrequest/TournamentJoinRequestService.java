package de.adesso.kicker.notification.tournamentjoinrequest;

import de.adesso.kicker.notification.NotificationRepository;
import de.adesso.kicker.notification.NotificationService;
import de.adesso.kicker.team.Team;
import de.adesso.kicker.team.TeamService;
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
    private NotificationService notificationService;
    private UserService userService;
    private SingleEliminationService singleEliminationService;

    @Autowired
    public TournamentJoinRequestService(NotificationRepository notificationRepository, UserService userService, NotificationService notificationService, SingleEliminationService singleEliminationService) {

        this.notificationRepository = notificationRepository;
        this.userService = userService;
        this.notificationService = notificationService;
        this.singleEliminationService = singleEliminationService;
    }
    
    public void saveTournamentJoinRequest(Tournament tournament, long senderId, Team team) {
        
        User sender = userService.getUserById(senderId);
        User receiver;
        if(team.getPlayerA() == userService.getUserById(senderId)) {
            receiver = team.getPlayerB();
        } else {
            receiver = team.getPlayerA();
        }
        TournamentJoinRequest request = new TournamentJoinRequest(tournament, sender, receiver, team);
        saveTournamentJoinRequest(request);
    }
    
    public void acceptTournamentJoinRequest(long notificationId) {
        
        TournamentJoinRequest request = (TournamentJoinRequest)notificationRepository.findByNotificationId(notificationId);
        singleEliminationService.addTeamToTournament((SingleElimination) request.getTargetTournament(), request.getTargetTeam());
        singleEliminationService.addPlayer((SingleElimination) request.getTargetTournament(), request.getTargetTeam().getPlayerA());
        singleEliminationService.addPlayer((SingleElimination) request.getTargetTournament(), request.getTargetTeam().getPlayerB());
        singleEliminationService.saveTournament((SingleElimination) request.getTargetTournament());
    }
    
    public void saveTournamentJoinRequest(TournamentJoinRequest tournamentJoinRequest) {
        notificationRepository.save(tournamentJoinRequest);
    }
    
}
