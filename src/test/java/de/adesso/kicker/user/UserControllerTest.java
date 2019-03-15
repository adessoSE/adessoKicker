package de.adesso.kicker.user;

import de.adesso.kicker.notification.message.MessageDummy;
import de.adesso.kicker.notification.persistence.Notification;
import de.adesso.kicker.notification.service.NotificationService;
import de.adesso.kicker.user.controller.UserController;
import de.adesso.kicker.user.exception.UserNotFoundException;
import de.adesso.kicker.user.persistence.User;
import de.adesso.kicker.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.keycloak.adapters.springboot.KeycloakAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource("classpath:application-test.properties")
@Import(KeycloakAutoConfiguration.class)
@WebMvcTest(value = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean(name = "userService")
    private UserService userService;

    @MockBean(name = "notificationService")
    private NotificationService notificationService;

    @Test
    @WithMockUser
    void whenUserLoggedInReturnUser() throws Exception {
        // given
        var user = UserDummy.defaultUser();
        List<Notification> notificationList = Collections.singletonList(MessageDummy.messageDeclined());
        given(userService.getLoggedInUser()).willReturn(user);
        given(notificationService.getNotificationsByReceiver(any(User.class))).willReturn(notificationList);

        // when
        var result = mockMvc.perform(get("/users/you"));

        // then
        result.andExpect(status().isOk()).andExpect(model().attribute("user", user));
    }

    @Test
    @WithMockUser
    void whenUserExistsReturnUser() throws Exception {
        // given
        var user = UserDummy.userWithLowRating();
        given(userService.getUserById(user.getUserId())).willReturn(user);

        // when
        var result = mockMvc.perform(get("/users/u/{id}", user.getUserId()));

        // then
        result.andExpect(status().isOk()).andExpect(model().attribute("user", user));
    }

    @Test
    void whenUserNotExistentExpectNotFound() throws Exception {
        // given
        willThrow(UserNotFoundException.class).given(userService).getUserById(anyString());

        // when
        var result = mockMvc.perform(get("/users/u/{id}", "non-existent-id"));

        // then
        result.andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void whenUserWithOutRankExpectNoStatistics() throws Exception {
        // given
        var user = UserDummy.defaultUser();
        var messages = ResourceBundle.getBundle("messages");
        given(userService.getUserById(user.getUserId())).willReturn(user);

        // when
        var result = mockMvc.perform(get("/users/u/{id}", user.getUserId()));

        // then
        result.andExpect(status().isOk())
                .andExpect(model().attribute("user", user))
                .andExpect(content().string(containsString(messages.getString("profile.noRank"))));
    }

    @Test
    @WithAnonymousUser
    void whenUserExistsReturnUserLoggedOut() throws Exception {
        // given
        var user = UserDummy.userWithLowRating();
        willThrow(UserNotFoundException.class).given(userService).getLoggedInUser();
        given(userService.getUserById(user.getUserId())).willReturn(user);

        // when
        var result = mockMvc.perform(get("/users/u/{id}", user.getUserId()));

        // then
        result.andExpect(status().isOk()).andExpect(model().attribute("user", user));
    }
}
