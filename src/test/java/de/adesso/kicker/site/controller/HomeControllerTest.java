package de.adesso.kicker.site.controller;

import de.adesso.kicker.notification.matchverificationrequest.MatchVerificationRequestDummy;
import de.adesso.kicker.notification.message.MessageDummy;
import de.adesso.kicker.notification.persistence.Notification;
import de.adesso.kicker.notification.service.NotificationService;
import de.adesso.kicker.user.UserDummy;
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

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource("classpath:application-test.properties")
@Import(KeycloakAutoConfiguration.class)
@WebMvcTest(HomeController.class)
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean(name = "userService")
    private UserService userService;

    @MockBean
    private NotificationService notificationService;

    private static User createUser() {
        return UserDummy.defaultUser();
    }

    private static List<Notification> createNotifications() {
        return List.of(MessageDummy.messageDeclined(), MatchVerificationRequestDummy.matchVerificationRequest());
    }

    private static List<User> createUserList() {
        return List.of(UserDummy.userWithLowRating(), UserDummy.userWithHighRating());
    }

    @Test
    @WithAnonymousUser
    void viewRendersWithAnonymousUser() throws Exception {
        // given
        var userList = createUserList();
        given(userService.getAllUsers()).willReturn(userList);
        given(userService.getUserPageSortedByRating(anyInt(), anyInt())).willReturn(userList);
        willThrow(UserNotFoundException.class).given(userService).getLoggedInUser();

        // when
        var result = mockMvc.perform(get("/"));

        // then
        result.andDo(print()).andExpect(status().isOk())
                .andExpect(model().attribute("user", false))
                .andExpect(model().attribute("notifications", false))
                .andExpect(model().attribute("users", userList))
                .andExpect(model().attribute("allUsers", userList))
                .andExpect(content().string(containsString("id=\"login-button\"")));
    }

    @Test
    @WithMockUser
    void viewRendersWithUser() throws Exception {
        // given
        var user = createUser();
        var notifications = createNotifications();
        var userList = createUserList();
        given(userService.getLoggedInUser()).willReturn(user);
        given(userService.getAllUsers()).willReturn(userList);
        given(notificationService.getNotificationsByReceiver(user)).willReturn(notifications);

        // when
        var result = mockMvc.perform(get("/"));

        // then
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", user))
                .andExpect(model().attribute("notifications", notifications))
                .andExpect(model().attribute("allUsers", userList))
                .andExpect(content().string(containsString("id=\"user-self\"")))
                .andExpect(content().string(containsString("id=\"notification-dropdown\"")))
                .andExpect(content().string(containsString("id=\"profile-dropdown\"")));
    }
}