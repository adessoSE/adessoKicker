package de.adesso.kicker.notification;

import de.adesso.kicker.notification.MatchVerificationRequest.VerifyMatchService;
import de.adesso.kicker.notification.exception.WrongReceiverException;
import de.adesso.kicker.user.UserDummy;
import de.adesso.kicker.user.UserService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import java.lang.reflect.Executable;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private VerifyMatchService verifyMatchService;

    @Mock
    private UserService userService;

    @InjectMocks
    private NotificationService notificationService;

    @BeforeAll
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    static Notification createNotifiation() {
        var notificationDummy = new NotificationDummy();
        return notificationDummy.notification();
    }

    static List<Notification> createNotificationList() {
        var notificationDummy = new NotificationDummy();
        return Collections.singletonList(notificationDummy.notification());
    }

    @Test
    @DisplayName("If the notification is valid it should be saved")
    void whenValidNotificationThenNotificationShouldBeSaved() {
        // given
        var notification = createNotifiation();
        when(userService.getLoggedInUser()).thenReturn(notification.getReceiver());
        when(notificationRepository.save(any(Notification.class))).thenAnswer((Answer<Notification>) invocation -> {
            Object[] args = invocation.getArguments();
            return (Notification) args[0];
        });

        // when
        notificationService.saveNotification(notification);

        // then
        verify(notificationRepository, times(1)).save(notification);
    }

    @Test
    @DisplayName("If the receiver is not the current user a WrongReceiverException should be thrown")
    void whenReceiverNotEqualCurrentUserThrowWrongReceiverException() {
        // given
        var notification = createNotifiation();
        when(userService.getLoggedInUser()).thenReturn(new UserDummy().alternateUser2());
        // when
        // Executable when = () -> notificationService.checkWrongReceiver(notification);
        // then
        // Assertions.assertThrows(WrongReceiverException.class, when);
    }

    @Test
    @DisplayName("If the notificationId is invalid a NotificationNotExistingException should be thrown")
    void whenNotificationNotExitingThrowNotificationNotExistingException() {

    }

    @Test
    @DisplayName("If a Notification with the given id exists it should be deleted")
    void whenValidNotificationThenNotificationShouldBeDeleted() {

    }

    @Test
    @DisplayName("should return all notifications for an user")
    void shouldReturnAllNotificationsForReceiver() {

    }

    @Test
    @DisplayName("should create and save a notifcation")
    void shouldCreateAndSaveNotification() {

    }

    @Test
    @DisplayName("should send a message to every user returned")
    void shouldSendDeclineMessageToEveryUserReturned() {

    }

    @Test
    @DisplayName("should accept request")
    void shouldAcceptRequest() {

    }
}
