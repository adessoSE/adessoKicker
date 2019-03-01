package de.adesso.kicker.notification.controller;

import de.adesso.kicker.notification.persistence.Notification;
import de.adesso.kicker.notification.service.NotificationService;
import de.adesso.kicker.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    private final UserService userService;

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
