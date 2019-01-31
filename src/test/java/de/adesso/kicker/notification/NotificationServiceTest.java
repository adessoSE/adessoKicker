package de.adesso.kicker.notification;

import de.adesso.kicker.user.User;
import de.adesso.kicker.user.UserDummy;
import de.adesso.kicker.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class NotificationServiceTest {

    @InjectMocks
    NotificationService notificationService;

    @Mock
    NotificationRepository notificationRepository;
    @Mock
    UserService userService;

    // Dummies
    private User user = new UserDummy().alternateUser();
    private User otherUser = new UserDummy().alternateUser2();

    private NotificationDummy notificationDummy = new NotificationDummy();
    private Notification notification = notificationDummy.defaultNotification();
    private Notification otherNotification = notificationDummy.alternateNotification();
    private Notification altNotification1 = notificationDummy.alternateNotification1();
    private Notification altNotification2 = notificationDummy.alternateNotification2();

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);

        // Say what to return when trying to get a user via UserService
        when(userService.getUserById(anyLong())).thenReturn(null);
        when(userService.getUserById(eq(user.getUserId()))).thenReturn(user);
        when(userService.getUserById(eq(otherUser.getUserId()))).thenReturn(otherUser);

        // Say what to return when trying to get a notification via NotificationService
        when(notificationRepository.findByNotificationId(anyLong())).thenReturn(null);
        when(notificationRepository.findByNotificationId(eq(notification.getNotificationId())))
                .thenReturn(notification);
        when(notificationRepository.findAll())
                .thenReturn(Arrays.asList(notification, otherNotification, altNotification1, altNotification2));
        when(notificationRepository.findByReceiver(any(User.class)))
                .thenReturn(Collections.singletonList(otherNotification));
        when(notificationRepository.findBySender(any(User.class))).thenReturn(Collections.singletonList(notification));
    }

    // Test --> Try adding a notification (type = Notification)
    @Test
    void saveNotification_NullNotification() {

        notificationService.saveNotification(notificationDummy.nullNotification());
    }

    @Test
    void saveNotification_ValidInput() {

        notificationService.saveNotification(otherUser.getUserId(), user.getUserId());
    }

    @Test
    void saveNotification_NullSender() {

        notificationService.saveNotification(otherUser.getUserId(), -1L);
    }

    @Test
    void saveNotification_NullReceiver() {

        notificationService.saveNotification(-1L, user.getUserId());
    }

    @Test
    void saveNotification_NullSenderAndReceiver() {

        notificationService.saveNotification(-1L, -1L);
    }

    @Test
    void saveNotification_Object() {

        notificationService.saveNotification(notification);
    }

    @Test
    void getNotificationById_ValidInput() {

        Notification n = notificationService.getNotificationById(notification.getNotificationId());
        assertEquals(notification, n);
    }

    @Test
    void getNotificationById_Null() {

        Notification n = notificationService.getNotificationById(-1L);
        assertNull(n);
    }

    @Test
    void getAllNotification() {

        List<Notification> notifications = notificationService.getAllNotifications();
        assertTrue(notifications.contains(notification));
    }

    @Test
    void getAllNotificationByReceiver() {

        List<Notification> notifications = notificationService.getAllNotificationsByReceiver(user.getUserId());
        assertTrue(notifications.contains(otherNotification));
    }

    @Test
    void getAllNotificationBySender() {

        List<Notification> notifications = notificationService.getAllNotificationsBySender(user.getUserId());
        assertTrue(notifications.contains(notification));
    }
}
