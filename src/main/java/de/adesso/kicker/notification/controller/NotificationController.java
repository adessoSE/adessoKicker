package de.adesso.kicker.notification.controller;

import de.adesso.kicker.notification.exception.NotificationNotFoundException;
import de.adesso.kicker.notification.exception.WrongReceiverException;
import de.adesso.kicker.notification.persistence.Notification;
import de.adesso.kicker.notification.service.NotificationService;
import de.adesso.kicker.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
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
    public ModelAndView declineNotification(@PathVariable long id) {
        var modelAndView = new ModelAndView();
        try {
            notificationService.declineNotification(id);
            modelAndView.addObject("successDeclined", true);
        } catch (NotificationNotFoundException e) {
            modelAndView.addObject("notExisting", true);
        } catch (WrongReceiverException e) {
            modelAndView.addObject("wrongReceiver", true);
        }
        modelAndView.setViewName("sites/notificationresult.html");
        return modelAndView;
    }

    @GetMapping("/accept/{id}")
    public ModelAndView acceptNotification(@PathVariable long id) {
        var modelAndView = new ModelAndView();
        try {
            notificationService.acceptNotification(id);
            modelAndView.addObject("successAccepted", true);
        } catch (NotificationNotFoundException e) {
            modelAndView.addObject("notExisting", true);
        } catch (WrongReceiverException e) {
            modelAndView.addObject("wrongReceiver", true);
        }
        modelAndView.setViewName("sites/notificationresult.html");
        return modelAndView;
    }

}
