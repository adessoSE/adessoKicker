package de.adesso.kicker.notification;

import de.adesso.kicker.notification.teamjoinrequest.TeamJoinRequestService;
import de.adesso.kicker.notification.tournamentjoinrequest.TournamentJoinRequestService;
import de.adesso.kicker.user.User;
import de.adesso.kicker.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    private NotificationRepository notificationRepository;
    private UserService userService;
    private TeamJoinRequestService teamJoinRequestService;
    private TournamentJoinRequestService tournamentJoinRequestService;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, UserService userService, TeamJoinRequestService teamJoinRequestService, TournamentJoinRequestService tournamentJoinRequestService) {

        this.notificationRepository = notificationRepository;
        this.userService = userService;
        this.teamJoinRequestService = teamJoinRequestService;
        this.tournamentJoinRequestService = tournamentJoinRequestService;
    }

    public Notification getNotificationById(long id) {

        return notificationRepository.findByNotificationId(id);
    }

    public List<Notification> getAllNotifications() {

        List<Notification> notifications = new ArrayList<>();
        notificationRepository.findAll().forEach(notifications::add);
        return notifications;
    }

    public List<Notification> getAllNotificationsBySender(long senderId) {

        User sender = userService.getUserById(senderId);
        List<Notification> notifications = new ArrayList<>();
        notificationRepository.findBySender(sender).forEach(notifications::add);
        return notifications;
    }

    public List<Notification> getAllNotificationsByReceiver(long receiverId) {

        User receiver = userService.getUserById(receiverId);
        List<Notification> notifications = new ArrayList<>();
        notificationRepository.findByReceiver(receiver).forEach(notifications::add);
        return notifications;
    }

    //Accepts a notification passed on type (enum)
    public void acceptNotificationById(long id){

        Notification n = getNotificationById(id);
        switch (n.getType()){
            case Notification:
                System.out.println("Notification with id: " + id);
                break;
            case TeamJoinRequest:
                teamJoinRequestService.acceptTeamJoinRequest(id);
                System.out.println("TeamJoinRequest with id: " + id);
                break;
            case MatchCreationRequest:
                System.out.println("MatchCreationRequest with id: " + id);
                break;
            case TournamentJoinRequest:
                tournamentJoinRequestService.acceptTournamentJoinRequest(id);
                System.out.println("TournamentJoinRequest with id: " + id);
                break;
        }
        removeNotificationById(id);
    }

    public void saveNotification(Notification notification) {

        if (notification == null) {
            System.err.println("ERROR at 'NotificationService' --> 'saveNotification()' : Given Notification is null");
            return;
        }
        notificationRepository.save(notification);
    }

    // Try to create a notification
    public Notification createNotification(long senderId, long receiverId, String message) {

        User sender = userService.getUserById(senderId);
        User receiver = userService.getUserById(receiverId);

        //Validation
        if (sender == null) {
            System.err.println("ERROR at 'NotificationService' --> 'createNotification()' : Cannot find sender with id " + senderId);
            return null;
        }
        if (receiver == null) {
            System.err.println("ERROR at 'NotificationService' --> 'createNotification()' : Cannot find receiver with id " + receiverId);
            return null;
        }

        Notification notification = new Notification(sender, receiver, message);
        return notification;
    }

    public void saveNotification(long senderId, long receiverId, String message) {

        saveNotification(createNotification(senderId, receiverId, message));
    }

    public void removeNotificationById(long id) {

        notificationRepository.deleteById(id);
    }
}
