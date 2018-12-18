package de.adesso.kicker.notification;

import java.util.List;

import de.adesso.kicker.notification.teamjoinrequest.TeamJoinRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class NotificationController {

    private NotificationService notificationService;
    private TeamJoinRequestService teamJoinRequestService;

    @Autowired
    public NotificationController(NotificationService notificationService, TeamJoinRequestService teamJoinRequestService) {

        this.notificationService = notificationService;
        this.teamJoinRequestService = teamJoinRequestService;
    }

    @RequestMapping("/notifications")
    public List<Notification> getNotifications() {

        return notificationService.getAllNotifications();
    }

    @RequestMapping("/{userId}/notifications/send")
    public List<Notification> getUserNotificationsSend(@PathVariable long userId) {

        return notificationService.getAllNotificationsBySender(userId);
    }

    @RequestMapping("/{userId}/notifications/received")
    public List<Notification> getUserNotificationsReceived(@PathVariable long userId) {

        return notificationService.getAllNotificationsByReceiver(userId);
    }

    @DeleteMapping("/notifications/accept/{id}")
    public void acceptNotificationById(@PathVariable long id) {

        notificationService.acceptNotificationById(id);
    }

    @DeleteMapping("/notifications/{id}")
    public void deleteNotificationById(@PathVariable long id) {

        notificationService.declineNotificationById(id);
    }

    @GetMapping("/notifications/add")
    public ModelAndView getAddNotifcation() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/notification/add_notification");
        return modelAndView;
    }

    @PostMapping("/notifications/add")
    public ModelAndView addNotifcation(Long senderId, Long receiverId, String message) {

        notificationService.saveNotification(senderId, receiverId, message);

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
    public ModelAndView getAddNotifcationTeamJoinRequest(Long senderId, Long receiverId, String teamName) {

        teamJoinRequestService.saveTeamJoinRequest(senderId, receiverId, teamName);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/notification/add_teamjoinrequest");
        return modelAndView;
    }
}
