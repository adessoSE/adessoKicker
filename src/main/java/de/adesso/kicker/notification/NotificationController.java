package de.adesso.kicker.notification;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import de.adesso.kicker.team.TeamService;
import de.adesso.kicker.user.UserService;

@RestController
public class NotificationController {
	
	private NotificationService notificationService;
	
	@Autowired
    public NotificationController(NotificationService notificationService) {
		
		this.notificationService = notificationService;
    }
	
	@RequestMapping("/notifications")
	public List<Notification> getNotifications() {
		
		return notificationService.getAllNotifications();
	}
}
