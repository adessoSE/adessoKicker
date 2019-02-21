package de.adesso.kicker.notification;

import de.adesso.kicker.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NotificationController {

    private NotificationService notificationService;

    private UserService userService;

    @Autowired
    public NotificationController(NotificationService notificationService, UserService userService) {
        this.notificationService = notificationService;
        this.userService = userService;
    }

    @GetMapping("/notifications/get")
    public List<Notification> getNotifications() {
        return notificationService.getNotificationsByReceiver(userService.getLoggedInUser());
    }

    @DeleteMapping("/notifications/decline/{id}")
    public void declineNotification(@PathVariable long id) {
        notificationService.declineNotification(id);
    }

    @DeleteMapping("/notifications/accept/{id}")
    public void acceptNotification(@PathVariable long id) {
        notificationService.acceptNotification(id);
    }

}
