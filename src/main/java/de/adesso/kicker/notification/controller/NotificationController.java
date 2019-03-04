package de.adesso.kicker.notification.controller;

import de.adesso.kicker.notification.service.NotificationService;
import de.adesso.kicker.notification.persistence.Notification;
import de.adesso.kicker.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
