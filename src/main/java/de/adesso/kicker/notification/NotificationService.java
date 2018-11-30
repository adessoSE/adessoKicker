package de.adesso.kicker.notification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.adesso.kicker.user.User;
import de.adesso.kicker.user.UserService;

@Service
public class NotificationService {

    private NotificationRepository notificationRepository;
    private UserService userService;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, UserService userService) {

        this.notificationRepository = notificationRepository;
        this.userService = userService;
    }

    // Gets a notification by id
    public Notification getNotificationById(long id) {

        return notificationRepository.findByNotificationId(id);
    }

    // Get all notifications
    public List<Notification> getAllNotifications() {

        List<Notification> notifications = new ArrayList<>();
        notificationRepository.findAll().forEach(notifications::add);
        return notifications;
    }

    // Get all notifications that were send by a specific user
    public List<Notification> getAllNotificationsBySender(long senderId) {

        User sender = userService.getUserById(senderId);
        List<Notification> notifications = new ArrayList<>();
        notificationRepository.findBySender(sender).forEach(notifications::add);
        return notifications;
    }

    // Get all notifications that were received by a specific user
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
                System.out.println("TeamJoinRequest with id: " + id);
                break;
            case MatchCreationRequest:
                System.out.println("MatchCreationRequest with id: " + id);
                break;
            case TournamentJoinRequest:
                System.out.println("TournamentJoinRequest with id: " + id);
                break;
        }
        removeNotificationById(id);
    }

    // Save notification in repository
    public void saveNotification(Notification notification) {

        if (notification == null) {
            System.err.println("ERROR at 'NotificationService' --> 'saveNotification()' : Given Notification is null");
            return;
        }
        notificationRepository.save(notification);
    }

    // Try to create a notification
    public Notification createNotification(String message, long receiverId, long senderId) {

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

        Notification notification = new Notification(message, receiver, sender);
        return notification;
    }

    public void saveNotification(String message, long receiverId, long senderId) {

        saveNotification(createNotification(message,receiverId,senderId));
    }

    // Removes notification from repository
    public void removeNotificationById(long id) {

        notificationRepository.deleteById(id);
    }
}
