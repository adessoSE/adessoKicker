package de.adesso.kicker.notification;

import de.adesso.kicker.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/notifications")
public class NotificationController {

    private NotificationService notificationService;

    private UserService userService;

    @Autowired
    public NotificationController(NotificationService notificationService, UserService userService) {
        this.notificationService = notificationService;
        this.userService = userService;
    }

    @GetMapping("/get")
    public List<Notification> getNotifications() {
        return notificationService.getNotificationsByReceiver(userService.getLoggedInUser());
    }

    @GetMapping("/decline/{id}")
    public void declineNotification(@PathVariable long id) {
        notificationService.declineNotification(id);
    }

    @GetMapping("/accept/{id}")
    public void acceptNotification(@PathVariable long id) {
        notificationService.acceptNotification(id);
    }

}
