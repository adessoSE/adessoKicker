package de.adesso.kicker.notification.controller;

import de.adesso.kicker.notification.exception.NotificationNotFoundException;
import de.adesso.kicker.notification.exception.WrongReceiverException;
import de.adesso.kicker.notification.persistence.Notification;
import de.adesso.kicker.notification.service.NotificationService;
import de.adesso.kicker.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String declineNotification(@PathVariable long id, Model model) {
        try {
            notificationService.declineNotification(id);
            model.addAttribute("successDeclined", true);
        } catch (NotificationNotFoundException e) {
            model.addAttribute("notExisting", true);
        } catch (WrongReceiverException e) {
            model.addAttribute("wrongReceiver", true);
        }
        return "sites/notificationresult.html";
    }

    @GetMapping("/accept/{id}")
    public String acceptNotification(@PathVariable long id, Model model) {
        try {
            notificationService.acceptNotification(id);
            model.addAttribute("successAccepted", true);
        } catch (NotificationNotFoundException e) {
            model.addAttribute("notExisting", true);
        } catch (WrongReceiverException e) {
            model.addAttribute("wrongReceiver", true);
        }
        return "sites/notificationresult.html";
    }
}
