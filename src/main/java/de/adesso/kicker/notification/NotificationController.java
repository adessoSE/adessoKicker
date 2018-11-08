package de.adesso.kicker.notification;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import de.adesso.kicker.notification.teamjoinrequest.TeamJoinRequest;
import de.adesso.kicker.team.Team;
import de.adesso.kicker.team.TeamService;
import de.adesso.kicker.user.User;
import de.adesso.kicker.user.UserService;

@RestController
public class NotificationController {
	
	private NotificationService notificationService;
	private UserService userService;
	private TeamService teamService;
	
	@Autowired
    public NotificationController(NotificationService notificationService, UserService userService, TeamService teamService) {
		
		this.notificationService = notificationService;
		this.userService = userService;
		this.teamService = teamService;
    }
	
	@RequestMapping("/notifications")
	public List<Notification> getNotifications() {
		
		return notificationService.getAllNotifications();
	}
	
	@RequestMapping("/{userId}/notifications/send")
    public List<Notification> getUserNotificationsSend(@PathVariable long userId) {
        
        return notificationService.getAllNotificationsBySender(userService.getUserById(userId));
    }
	
	@RequestMapping("/{userId}/notifications/received")
    public List<Notification> getUserNotificationsReceived(@PathVariable long userId) {
        
	    return notificationService.getAllNotificationsByReceiver(userService.getUserById(userId));
    }
	
	@DeleteMapping("/notifications/{id}")
	public void deleteNotificationById(@PathVariable long id) {
	    
	    notificationService.removeNotificationById(id);
	}
	
	@RequestMapping("/notifications/add/notification")
	public ModelAndView getAddNotifcationStandart() {
	    
	    ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("/notification/add_notification");
	    return modelAndView;
	}
	
	@PostMapping("/notifications/add/notifcation")
    public ModelAndView addNotifcationStandart(Long senderId, Long receiverId, String message) {
        
        notificationService.saveNotification(message, receiverId, senderId);
        
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/notification/add_notification");
        return modelAndView;
    }
	
	@RequestMapping("/notifications/add/teamjoinrequest")
    public ModelAndView getAddNotifcationTeamJoinRequest() {
        
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/notification/add_teamjoinrequest");
        return modelAndView;
    }
    
    @PostMapping("/notifications/add/teamjoinrequest")
    public ModelAndView getAddNotifcationTeamJoinRequest(Long senderId, Long receiverId, Long teamId){
        
        //TO_DO LOGIC AUSLAGERN
        User sender = userService.getUserById(senderId);
        User receiver = userService.getUserById(receiverId);
        Team targetTeam = teamService.getTeamById(teamId);
        
        TeamJoinRequest notification = new TeamJoinRequest(targetTeam, sender, receiver);
        notificationService.saveNotification(notification);
        
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/notification/add_teamjoinrequest");
        return modelAndView;
    }
}
