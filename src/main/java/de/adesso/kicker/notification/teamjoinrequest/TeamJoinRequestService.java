package de.adesso.kicker.notification.teamjoinrequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.adesso.kicker.notification.Notification;
import de.adesso.kicker.notification.NotificationRepository;
import de.adesso.kicker.notification.NotificationService;
import de.adesso.kicker.team.IdenticalPlayersException;
import de.adesso.kicker.team.Team;
import de.adesso.kicker.team.TeamNameExistingException;
import de.adesso.kicker.team.TeamService;
import de.adesso.kicker.user.User;
import de.adesso.kicker.user.UserService;

@Service
public class TeamJoinRequestService {

    private NotificationRepository notificationRepository;
    private NotificationService notificationService;
    private UserService userService;
    private TeamService teamService;

    @Autowired
    public TeamJoinRequestService(NotificationRepository notificationRepository, UserService userService, TeamService teamService, NotificationService notificationService) {

        this.notificationRepository = notificationRepository;
        this.userService = userService;
        this.teamService = teamService;
        this.notificationService = notificationService;
    }

    // Try to create a notification
    public TeamJoinRequest createTeamJoinRequest(String teamName, long receiverId, long senderId) {

        User sender = userService.getUserById(senderId);
        User receiver = userService.getUserById(receiverId);

        //Validation
        if (sender == null) {
            System.err.println("ERROR at 'TeamJoinRequestService' --> 'createTeamJoinRequest()' : Cannot find sender with id " + senderId);
            return null;
        }
        if (receiver == null) {
            System.err.println("ERROR at 'TeamJoinRequestService' --> 'createTeamJoinRequest()' : Cannot find receiver with id " + receiverId);
            return null;
        }

        TeamJoinRequest teamJoinRequest = new TeamJoinRequest(teamName, sender, receiver);
        return teamJoinRequest;
    }

    public void saveTeamJoinRequest(String teamName, long receiverId, long senderId) {

        saveTeamJoinRequest(createTeamJoinRequest(teamName, receiverId, senderId));
    }
    
    public void acceptTeamJoinRequest(long notificationId) {
        
        TeamJoinRequest request = (TeamJoinRequest)notificationRepository.findByNotificationId(notificationId);
        if (request == null){

            System.err.println("ERROR at 'TeamJoinRequestService' --> 'acceptTeamJoinRequest()' : Cannot find TeamJoinRequest with id " + notificationId);
            return;
        }
        Team team = new Team(request.getTeamName(), request.getSender(), request.getReceiver());
        teamService.saveTeam(team);
    }
    
    public void saveTeamJoinRequest(TeamJoinRequest teamJoinRequest) {

        if (teamJoinRequest == null ) {
            System.err.println("ERROR at 'TeamJoinRequestService' --> 'saveTeamJoinRequest()' : Given TeamJoinRequest is null");
            return;
        }
        notificationRepository.save(teamJoinRequest);
    }
    
}
