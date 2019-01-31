package de.adesso.kicker.notification;

import de.adesso.kicker.notification.matchcreationrequest.MatchCreationRequestService;
import de.adesso.kicker.notification.matchverificationrequest.MatchVerificationRequestService;
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
    private MatchCreationRequestService matchCreationRequestService;
    private MatchVerificationRequestService matchVerificationRequestService;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, UserService userService,
            TeamJoinRequestService teamJoinRequestService, TournamentJoinRequestService tournamentJoinRequestService,
            MatchCreationRequestService matchCreationRequestService,
            MatchVerificationRequestService matchVerificationRequestService) {

        this.notificationRepository = notificationRepository;
        this.userService = userService;
        this.teamJoinRequestService = teamJoinRequestService;
        this.tournamentJoinRequestService = tournamentJoinRequestService;
        this.matchCreationRequestService = matchCreationRequestService;
        this.matchVerificationRequestService = matchVerificationRequestService;
    }

    public Notification getNotificationById(long id) {

        return notificationRepository.findByNotificationId(id);
    }

    public List<Notification> getAllNotifications() {

        List<Notification> notifications = new ArrayList<>();
        notificationRepository.findAll().forEach(notifications::add);
        return notifications;
    }

    public List<Notification> getAllNotificationsBySender(User sender) {

        List<Notification> notifications = new ArrayList<>();
        if (sender != null) {
            notifications = getAllNotificationsBySender(sender.getUserId());
        }
        return notifications;
    }

    public List<Notification> getAllNotificationsByReceiver(User receiver) {

        List<Notification> notifications = new ArrayList<>();
        if (receiver != null) {
            notifications = getAllNotificationsByReceiver(receiver.getUserId());
        }
        return notifications;
    }

    public List<Notification> getAllNotificationsBySender(long senderId) {

        User sender = userService.getUserById(senderId);
        return new ArrayList<>(notificationRepository.findBySender(sender));
    }

    public List<Notification> getAllNotificationsByReceiver(long receiverId) {

        User receiver = userService.getUserById(receiverId);
        return new ArrayList<>(notificationRepository.findByReceiver(receiver));
    }

    // Accepts a notification passed on type (enum)
    public void acceptNotificationById(long id) {

        Notification notification = getNotificationById(id);
        switch (notification.getType()) {
        case NOTIFICATION:
            removeNotificationById(id);
            break;
        case TEAM_JOIN_REQUEST:
            teamJoinRequestService.acceptTeamJoinRequest(id);
            removeNotificationById(id);
            break;
        case MATCH_CREATION_REQUEST:
            matchCreationRequestService.acceptMatchJoinRequest(id);
            break;
        case TOURNAMENT_JOIN_REQUEST:
            tournamentJoinRequestService.acceptTournamentJoinRequest(id);
            removeNotificationById(id);
            break;
        case MATCH_VERIFICATION_REQUEST:
            matchVerificationRequestService.acceptMatchVerificationRequest(id);
            removeNotificationById(id);
            break;
        }
    }

    public void saveNotification(Notification notification) {

        if (notification == null) {
            System.err.println("ERROR at 'NotificationService' --> 'saveNotification()' : Given Notification is null");
            return;
        }
        notificationRepository.save(notification);
    }

    public void saveNotification(long senderId, long receiverId) {

        saveNotification(createNotification(senderId, receiverId));
    }

    // Try to create a notification
    public Notification createNotification(long senderId, long receiverId) {

        User sender = userService.getUserById(senderId);
        User receiver = userService.getUserById(receiverId);

        // Validation
        if (sender == null) {
            System.err.println("ERROR at 'NotificationService' --> 'createNotification()' : Cannot find sender with id "
                    + senderId);
            return null;
        }
        if (receiver == null) {
            System.err
                    .println("ERROR at 'NotificationService' --> 'createNotification()' : Cannot find receiver with id "
                            + receiverId);
            return null;
        }

        return new Notification(sender, receiver);
    }

    public void removeNotificationById(long id) {

        notificationRepository.deleteById(id);
    }

    public void declineNotificationById(long id) {

        Notification notification = getNotificationById(id);
        switch (notification.getType()) {
        case MATCH_CREATION_REQUEST:
            matchCreationRequestService.declineMatchJoinRequest(id);
            break;
        case MATCH_VERIFICATION_REQUEST:
            matchVerificationRequestService.declineMatchVerificationRequest(id);
            break;
        default:
            removeNotificationById(id);
        }
    }
}
