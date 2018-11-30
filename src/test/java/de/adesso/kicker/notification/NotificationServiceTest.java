package de.adesso.kicker.notification;

import de.adesso.kicker.notification.teamjoinrequest.TeamJoinRequestService;
import de.adesso.kicker.user.User;
import de.adesso.kicker.user.UserDummy;
import de.adesso.kicker.user.UserRepository;
import de.adesso.kicker.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
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

    @Before
    public void setUp(){

        MockitoAnnotations.initMocks(this);

        //Say what to return when trying to get a user via UserService
        when(userService.getUserById(anyLong())).thenReturn(user);
        when(userService.getUserById(eq(user.getUserId()))).thenReturn(user);
        when(userService.getUserById(eq(otherUser.getUserId()))).thenReturn(otherUser);
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
}
