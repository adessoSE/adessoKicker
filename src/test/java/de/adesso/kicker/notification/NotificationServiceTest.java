package de.adesso.kicker.notification;

import de.adesso.kicker.notification.matchverificationrequest.persistence.MatchVerificationRequest;
import de.adesso.kicker.notification.matchverificationrequest.MatchVerificationRequestDummy;
import de.adesso.kicker.notification.matchverificationrequest.service.VerifyMatchService;
import de.adesso.kicker.notification.message.persistence.Message;
import de.adesso.kicker.notification.message.MessageDummy;
import de.adesso.kicker.notification.message.persistence.MessageType;
import de.adesso.kicker.notification.message.service.SendMessageService;
import de.adesso.kicker.notification.exception.NotificationNotExistingException;
import de.adesso.kicker.notification.exception.WrongReceiverException;
import de.adesso.kicker.notification.persistence.Notification;
import de.adesso.kicker.notification.persistence.NotificationRepository;
import de.adesso.kicker.notification.service.NotificationService;
import de.adesso.kicker.user.persistence.User;
import de.adesso.kicker.user.UserDummy;
import de.adesso.kicker.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private VerifyMatchService verifyMatchService;

    @Mock
    private SendMessageService sendMessageService;

    @Mock
    private UserService userService;

    @InjectMocks
    private NotificationService notificationService;

    static Notification createMessageDeclined() {
        return MessageDummy.messageDeclined();
    }

    static Notification createMatchVerification() {
        return MatchVerificationRequestDummy.matchVerificationRequest();
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    static List<Notification> notificationList() {
        return List.of(MessageDummy.messageDeclined(), MatchVerificationRequestDummy.matchVerificationRequest());
    }

    @Test
    @DisplayName("If the notification does not exists a NotificationNotExistingException should be thrown")
    void whenInvalidIdThenExceptionShouldBeThrown() {
        // given
        when(notificationRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when
        Executable when = () -> notificationService.getNotificationById(anyLong());

        // then
        assertThrows(NotificationNotExistingException.class, when);
    }

    @Test
    @DisplayName("If the receiver of the notification is not the current user a WrongReceiverException should be thrown")
    void whenWrongReceiverThenThrowWrongReceiverException() {
        // given
        var notification = createMatchVerification();
        when(userService.getLoggedInUser()).thenReturn(UserDummy.alternateUser2());

        // when
        Executable when = () -> notificationService.checkWrongReceiver(notification);

        // then
        assertThrows(WrongReceiverException.class, when);
    }

    @Test
    @DisplayName("If message a is accepted sendMessageService.deleteMessage() should be called")
    void whenMessageAcceptedThenDeleteMessageShouldBeCalled() {
        // given
        var message = createMessageDeclined();
        when(notificationRepository.findById(anyLong())).thenReturn(Optional.of(message));
        when(userService.getLoggedInUser()).thenReturn(message.getReceiver());

        // when
        notificationService.acceptNotification(1);

        // then
        verify(sendMessageService, times(1)).deleteMessage((Message) message);
    }

    @Test
    @DisplayName("If matchVerification a is accepted verifyMatchService.acceptRequest() should be called")
    void whenMatchVerificationAcceptedThenAcceptRequestShouldBeCalled() {
        // given
        var matchVerification = createMatchVerification();
        when(notificationRepository.findById(anyLong())).thenReturn(Optional.of(matchVerification));
        when(userService.getLoggedInUser()).thenReturn(matchVerification.getReceiver());

        // when
        notificationService.acceptNotification(1);

        // then
        verify(verifyMatchService, times(1)).acceptRequest((MatchVerificationRequest) matchVerification);
    }

    @Test
    @DisplayName("If message a is deleted sendMessageService.deleteMessage() should be called")
    void whenMessageDeletedThenDeleteMessageShouldBeCalled() {
        // given
        var message = createMessageDeclined();
        when(notificationRepository.findById(anyLong())).thenReturn(Optional.of(message));
        when(userService.getLoggedInUser()).thenReturn(message.getReceiver());

        // when
        notificationService.declineNotification(1);

        // then
        verify(sendMessageService, times(1)).deleteMessage((Message) message);
    }

    @Test
    @DisplayName("If matchVerification a is declined verifyMatchService.declineRequest() should be called "
            + "and messages equals players in match -1 should be sent")
    void whenMatchVerificationDeclinedThenDeclineRequestShouldBeCalledAndMessagesSent() {
        // given
        var matchVerification = createMatchVerification();
        when(notificationRepository.findById(anyLong())).thenReturn(Optional.of(matchVerification));
        when(userService.getLoggedInUser()).thenReturn(matchVerification.getReceiver());
        when(verifyMatchService.declineRequest(any(MatchVerificationRequest.class)))
                .thenReturn(Collections.singletonList(UserDummy.defaultUser()));

        // when
        notificationService.declineNotification(1);

        // then
        verify(verifyMatchService, times(1)).declineRequest((MatchVerificationRequest) matchVerification);
        verify(sendMessageService, times(1)).sendMessage(any(User.class), any(User.class),
                eq(MessageType.MESSAGE_DECLINED));
    }

    @Test
    @DisplayName("Should return list of notifications with receiver")
    void shouldReturnNotificationsWithReceiver() {
        // given
        var notifications = notificationList();
        var user = UserDummy.defaultUser();
        given(notificationRepository.findAllByReceiver(user)).willReturn(notifications);

        // when
        var actualList = notificationService.getNotificationsByReceiver(user);

        // then
        assertEquals(notifications, actualList);
    }
}
