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
    
    public TeamJoinRequest saveTeamJoinRequest(String teamName, long receiverId, long senderId) {
        
        User sender = userService.getUserById(senderId);
        User receiver = userService.getUserById(receiverId);
        TeamJoinRequest request = new TeamJoinRequest(teamName, receiver, sender);
        saveTeamJoinRequest(request);
        return request;
    }
    
    public void acceptTeamJoinRequest(long notificationId) {
        
        TeamJoinRequest request = (TeamJoinRequest)notificationRepository.findByNotificationId(notificationId);
        Team team = new Team(request.getTeamName(), request.getSender(), request.getReceiver());
        teamService.saveTeam(team);
    }
    
    public void saveTeamJoinRequest(TeamJoinRequest teamJoinRequest) {
        notificationRepository.save(teamJoinRequest);
    }
    
}
