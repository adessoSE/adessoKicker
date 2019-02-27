package de.adesso.kicker.notification.service;

import de.adesso.kicker.notification.matchverificationrequest.persistence.MatchVerificationRequest;
import de.adesso.kicker.notification.matchverificationrequest.service.VerifyMatchService;
import de.adesso.kicker.notification.message.persistence.Message;
import de.adesso.kicker.notification.message.persistence.MessageType;
import de.adesso.kicker.notification.message.service.SendMessageService;
import de.adesso.kicker.notification.exception.NotificationNotExistingException;
import de.adesso.kicker.notification.exception.WrongReceiverException;
import de.adesso.kicker.notification.persistence.Notification;
import de.adesso.kicker.notification.persistence.NotificationRepository;
import de.adesso.kicker.user.persistence.User;
import de.adesso.kicker.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    private final VerifyMatchService verifyMatchService;

    private final SendMessageService sendMessageService;

    private final UserService userService;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, VerifyMatchService verifyMatchService,
            UserService userService, SendMessageService sendMessageService) {
        this.notificationRepository = notificationRepository;
        this.verifyMatchService = verifyMatchService;
        this.sendMessageService = sendMessageService;
        this.userService = userService;
    }

    public void acceptNotification(long notificationId) {
        Notification notification = getNotificationById(notificationId);
        checkWrongReceiver(notification);

        switch (notification.getType()) {
        case MESSAGE:
            sendMessageService.deleteMessage((Message) notification);
            break;
        case MATCH_VERIFICATION:
            verifyMatchService.acceptRequest((MatchVerificationRequest) notification);
            break;
        }
    }

    public void declineNotification(long notificationId) {
        Notification notification = getNotificationById(notificationId);
        checkWrongReceiver(notification);

        switch (notification.getType()) {
        case MESSAGE:
            sendMessageService.deleteMessage((Message) notification);
            break;
        case MATCH_VERIFICATION:
            List<User> users = verifyMatchService.declineRequest((MatchVerificationRequest) notification);
            User sender = userService.getLoggedInUser();
            for (User user : users) {
                sendMessageService.sendMessage(sender, user, MessageType.MESSAGE_DECLINED);
            }
            break;
        }
    }

    public List<Notification> getNotificationsByReceiver(User receiver) {
        return notificationRepository.findAllByReceiver(receiver);
    }

    public void checkWrongReceiver(Notification notification) {
        if (notification.getReceiver() != userService.getLoggedInUser()) {
            throw new WrongReceiverException();
        }
    }

    public Notification getNotificationById(long notificationId) {
        return notificationRepository.findById(notificationId).orElseThrow(NotificationNotExistingException::new);
    }
}
