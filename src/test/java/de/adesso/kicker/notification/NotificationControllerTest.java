package de.adesso.kicker.notification;

import de.adesso.kicker.notification.controller.NotificationController;
import de.adesso.kicker.notification.exception.NotificationNotFoundException;
import de.adesso.kicker.notification.exception.WrongReceiverException;
import de.adesso.kicker.notification.message.MessageDummy;
import de.adesso.kicker.notification.service.NotificationService;
import de.adesso.kicker.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.keycloak.adapters.springboot.KeycloakAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.doThrow;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource("classpath:application-test.properties")
@Import(KeycloakAutoConfiguration.class)
@WebMvcTest(value = NotificationController.class)
class NotificationControllerTest {

    @MockBean
    NotificationService notificationService;

    @MockBean
    UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("A notification should be accepted")
    @WithMockUser
    void acceptValidNotification() throws Exception {
        // given
        var notification = MessageDummy.messageDeclined();

        // when
        var result = this.mockMvc.perform(get("/notifications/accept/" + notification.getNotificationId()));

        // then
        then(notificationService).should(times(1)).acceptNotification(notification.getNotificationId());
        result.andExpect(status().isOk())
                .andExpect(view().name("sites/notificationresult.html"))
                .andExpect(model().attribute("successAccepted", true));
    }

    @Test
    @DisplayName("A notification should be declined")
    @WithMockUser
    void declineValidNotification() throws Exception {
        // given
        var notification = MessageDummy.messageDeclined();

        // when
        var result = this.mockMvc.perform(get("/notifications/decline/" + notification.getNotificationId()));

        // then
        then(notificationService).should(times(1)).declineNotification(notification.getNotificationId());
        result.andExpect(status().isOk())
                .andExpect(view().name("sites/notificationresult.html"))
                .andExpect(model().attribute("successDeclined", true));
    }

    @Test
    @DisplayName("An error message should be send if the notification does not exist")
    @WithMockUser
    void AcceptNotExistingNotification() throws Exception {
        // given
        var notificationId = 1;
        doThrow(NotificationNotFoundException.class).when(notificationService).acceptNotification(notificationId);

        // when
        var result = this.mockMvc.perform(get("/notifications/accept/" + notificationId));

        // then
        willThrow(NotificationNotFoundException.class).given(notificationService).acceptNotification(anyLong());
        result.andExpect(status().isOk())
                .andExpect(view().name("sites/notificationresult.html"))
                .andExpect(model().attribute("notExisting", true));
    }

    @Test
    @DisplayName("An error message should be send if the user is not the receiver")
    @WithMockUser
    void AcceptWrongReceiver() throws Exception {
        // given
        var notificationId = 1;
        doThrow(WrongReceiverException.class).when(notificationService).acceptNotification(notificationId);

        // when
        var result = this.mockMvc.perform(get("/notifications/accept/" + notificationId));

        // then
        willThrow(WrongReceiverException.class).given(notificationService).acceptNotification(anyLong());
        result.andExpect(status().isOk())
                .andExpect(view().name("sites/notificationresult.html"))
                .andExpect(model().attribute("wrongReceiver", true));
    }
}
