package de.adesso.kicker.notification.teamjoinrequest;

import de.adesso.kicker.notification.NotificationRepository;
import de.adesso.kicker.team.Team;
import de.adesso.kicker.team.TeamService;
import de.adesso.kicker.user.User;
import de.adesso.kicker.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamJoinRequestService {

    private NotificationRepository notificationRepository;
    private UserService userService;
    private TeamService teamService;

    @Autowired
    public TeamJoinRequestService(NotificationRepository notificationRepository, UserService userService, TeamService teamService) {

        this.notificationRepository = notificationRepository;
        this.userService = userService;
        this.teamService = teamService;
    }

    // Try to create a notification
    public TeamJoinRequest createTeamJoinRequest(long senderId, long receiverId, String teamName) {

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

        TeamJoinRequest teamJoinRequest = new TeamJoinRequest(receiver, sender, teamName);
        return teamJoinRequest;
    }

    public void saveTeamJoinRequest(long senderId, long receiverId, String teamName) {

        saveTeamJoinRequest(createTeamJoinRequest(senderId, receiverId, teamName));
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
