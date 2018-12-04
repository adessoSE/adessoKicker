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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class NotificationServiceTest {

    @InjectMocks
    NotificationService notificationService;

    @Mock
    NotificationRepository notificationRepository;
    @Mock
    UserService userService;

    //Dummies
    User user = new UserDummy().alternateUser();
    User otherUser = new UserDummy().alternateUser2();

    Notification notification = new NotificationDummy().defaultNotification();
    Notification otherNotification = new NotificationDummy().alternateNotification();
    Notification altNotification1 = new NotificationDummy().alternateNotification1();
    Notification altNotification2 = new NotificationDummy().alternateNotification2();

    @BeforeEach
    public void setUp(){

        MockitoAnnotations.initMocks(this);

        //Say what to return when trying to get a user via UserService
        when(userService.getUserById(anyLong())).thenReturn(null);
        when(userService.getUserById(eq(user.getUserId()))).thenReturn(user);
        when(userService.getUserById(eq(otherUser.getUserId()))).thenReturn(otherUser);

        //Say what to return when trying to get a notification via NotificationService
        when(notificationRepository.findByNotificationId(anyLong())).thenReturn(null);
        when(notificationRepository.findByNotificationId(eq(notification.getNotificationId()))).thenReturn(notification);
        when(notificationRepository.findAll()).thenReturn(Arrays.asList(notification, otherNotification, altNotification1, altNotification2));
        when(notificationRepository.findByReceiver(any(User.class))).thenReturn(Arrays.asList(otherNotification));
        when(notificationRepository.findBySender(any(User.class))).thenReturn(Arrays.asList(notification));
    }

    //Test --> Try adding a notification (type = Notification)
    @Test
    public void saveNotification_ValidInput(){

        notificationService.saveNotification(anyString(), user.getUserId(), otherUser.getUserId());
    }
    @Test
    public void saveNotification_NullMessage(){

        notificationService.saveNotification(null, user.getUserId(), otherUser.getUserId());
    }
    @Test
    public void saveNotification_NullSender(){

        notificationService.saveNotification(anyString(), -1L, otherUser.getUserId());
    }
    @Test
    public void saveNotification_NullReceiver(){

        notificationService.saveNotification(anyString(), user.getUserId(), -1L);
    }
    @Test
    public void saveNotification_NullSenderAndReceiver(){

        notificationService.saveNotification(anyString(), -1L, -1L);
    }
    @Test
    public void saveNotification_Object(){

        notificationService.saveNotification(notification);
    }
    @Test
    public void getNotificationById_ValidInput(){

        Notification n = notificationService.getNotificationById(notification.getNotificationId());
        assertEquals(notification, n);
    }
    @Test
    public void getNotificationById_Null(){

        Notification n = notificationService.getNotificationById(-1L);
        assertEquals(null, n);
    }
    @Test
    public void getAllNotification(){

        List<Notification> notifications = notificationService.getAllNotifications();
        assertTrue(notifications.contains(notification));
    }
    @Test
    public void getAllNotificationByReceiver(){

        List<Notification> notifications = notificationService.getAllNotificationsByReceiver(user.getUserId());
        assertTrue(notifications.contains(otherNotification));
    }
    @Test
    public void getAllNotificationBySender(){

        List<Notification> notifications = notificationService.getAllNotificationsBySender(user.getUserId());
        assertTrue(notifications.contains(notification));
    }
}
