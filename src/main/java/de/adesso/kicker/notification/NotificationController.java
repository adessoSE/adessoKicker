package de.adesso.kicker.notification;

import java.util.List;

import de.adesso.kicker.notification.teamjoinrequest.TeamJoinRequestService;
import javassist.expr.Instanceof;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import de.adesso.kicker.notification.teamjoinrequest.TeamJoinRequest;
import de.adesso.kicker.team.Team;
import de.adesso.kicker.team.TeamService;
import de.adesso.kicker.user.User;
import de.adesso.kicker.user.UserService;

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

        notificationService.removeNotificationById(id);
    }

    @GetMapping("/notifications/add/notification")
    public ModelAndView getAddNotifcationStandard() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/notification/add_notification");
        return modelAndView;
    }

    @PostMapping("/notifications/add/notification")
    public ModelAndView addNotifcationStandard(Long senderId, Long receiverId, String message) {

        notificationService.saveNotification(message, receiverId, senderId);

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
    public ModelAndView getAddNotifcationTeamJoinRequest(String teamName, Long receiverId, Long senderId) {

        teamJoinRequestService.saveTeamJoinRequest(teamName, receiverId, senderId);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/notification/add_teamjoinrequest");
        return modelAndView;
    }
}
