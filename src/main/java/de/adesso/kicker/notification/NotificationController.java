package de.adesso.kicker.notification;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import de.adesso.kicker.team.TeamService;
import de.adesso.kicker.user.User;
import de.adesso.kicker.user.UserService;

@RestController
public class NotificationController {
	
	private NotificationService notificationService;
	private UserService userService;
	
	@Autowired
    public NotificationController(NotificationService notificationService, UserService userService) {
		
		this.notificationService = notificationService;
		this.userService = userService;
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
	
	@RequestMapping("/notifications/add/standard")
	public ModelAndView getAddNotifcationStandart() {
	    
	    ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("/notification/notificationAdd_notification");
	    return modelAndView;
	}
	
	@PostMapping("/notifications/add/standard")
    public void getAddNotifcationStandart(Long senderId, Long receiverId, String message) {
        
        User sender = userService.getUserById(senderId);
        User receiver = userService.getUserById(receiverId);
        Notification notification = new Notification(message, receiver, sender);
        notificationService.saveNotification(notification);
    }
}
