package de.adesso.kicker.notification;

import java.util.List;

import de.adesso.kicker.notification.teamjoinrequest.TeamJoinRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class NotificationController {

    private NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {

        this.notificationService = notificationService;
    }

    @GetMapping("/notifications")
    public List<Notification> getAllNotifications() {

        return notificationService.getAllNotifications();
    }

    @GetMapping("/{userId}/notifications/send")
    public List<Notification> getUserNotificationsSend(@PathVariable long userId) {

        return notificationService.getAllNotificationsBySender(userId);
    }

    @GetMapping("/{userId}/notifications/received")
    public List<Notification> getUserNotificationsReceived(@PathVariable long userId) {

        return notificationService.getAllNotificationsByReceiver(userId);
    }

    @DeleteMapping("/notifications/accept/{id}")
    public void acceptNotificationById(@PathVariable long id) {

        notificationService.acceptNotificationById(id);
    }

    @DeleteMapping("/notifications/decline/{id}")
    public void declineNotificationById(@PathVariable long id) {

        notificationService.declineNotificationById(id);
    }

    @GetMapping("/notifications/add")
    public ModelAndView getNotificationAddPage() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/notification/add");
        return modelAndView;
    }

    @PostMapping("/notifications/add")
    public ModelAndView addNotificationAddPage(Long senderId, Long receiverId) {

        notificationService.saveNotification(senderId, receiverId);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/notification/add");
        return modelAndView;
    }
}
