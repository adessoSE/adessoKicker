package de.adesso.kicker.notification;

import de.adesso.kicker.notification.MatchVerificationRequest.MatchVerificationRequest;
import de.adesso.kicker.notification.MatchVerificationRequest.VerifyMatchService;
import de.adesso.kicker.notification.exception.NotificationNotExistingException;
import de.adesso.kicker.notification.exception.WrongReceiverException;
import de.adesso.kicker.user.User;
import de.adesso.kicker.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificationService {

    private NotificationRepository notificationRepository;
    private VerifyMatchService verifyMatchService;
    private UserService userService;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, VerifyMatchService verifyMatchService,
            UserService userService) {

        this.notificationRepository = notificationRepository;
        this.verifyMatchService = verifyMatchService;
        this.userService = userService;
    }

    public void acceptNotification(long notificationId) {

        checkNotificationExists(notificationId);
        Notification notification = notificationRepository.findByNotificationId(notificationId);
        checkWrongReceiver(notification);

        switch (notification.getType()) {
        case MESSAGE:
            deleteNotification(notification);
            break;
        case MATCH_VERIFICATION:
            verifyMatchService.acceptRequest((MatchVerificationRequest) notification);
            break;
        }
    }

    public void declineNotification(long notificationId) {

        checkNotificationExists(notificationId);
        Notification notification = notificationRepository.findByNotificationId(notificationId);
        checkWrongReceiver(notification);

        switch (notification.getType()) {
        case MESSAGE:
            deleteNotification(notification);
            break;
        case MATCH_VERIFICATION:
            // TODO delete the match
            List<User> users = verifyMatchService.declineRequest((MatchVerificationRequest) notification);
            for (User user : users) {
                sendNotification(null, user, userService.getLoggedInUser().getFirstName()
                        + " hat die Anfrage für das Match am "
                        + (((MatchVerificationRequest) notification).getMatch().getDate().toString() + " abgelehnt."));
            }
            break;
        }
    }

    public List<Notification> getNotificationsByReceiver(User receiver) {

        return notificationRepository.findAllByReceiver(receiver);
    }

    public void sendNotification(User sender, User receiver, String message) {

        Notification notification = new Notification(sender, receiver, message);
        notificationRepository.save(notification);
    }

    public void deleteNotification(Notification notification) {

        notificationRepository.delete(notification);
    }

    public void checkWrongReceiver(Notification notification) {

        if (notification.getReceiver() != userService.getLoggedInUser()) {
            throw new WrongReceiverException();
        }
    }

    public void checkNotificationExists(long notificationId) {

        if (!notificationRepository.existsById(notificationId)) {
            throw new NotificationNotExistingException(notificationId);
        }
    }
}
