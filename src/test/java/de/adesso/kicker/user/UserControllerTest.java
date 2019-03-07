package de.adesso.kicker.user;

import de.adesso.kicker.notification.message.MessageDummy;
import de.adesso.kicker.notification.persistence.Notification;
import de.adesso.kicker.notification.service.NotificationService;
import de.adesso.kicker.ranking.service.RankingService;
import de.adesso.kicker.user.controller.UserController;
import de.adesso.kicker.user.persistence.User;
import de.adesso.kicker.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = UserController.class, secure = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private RankingService rankingService;

    @MockBean
    private NotificationService notificationService;

    @Test
    @WithMockUser
    void whenUserLoggedInReturnUser() throws Exception {
        // given
        var user = UserDummy.defaultUser();
        List<Notification> notificationList = Collections.singletonList(MessageDummy.messageDeclined());
        given(userService.getLoggedInUser()).willReturn(user);
        given(rankingService.getPositionOfPlayer(user.getRanking())).willReturn(1);
        given(notificationService.getNotificationsByReceiver(any(User.class))).willReturn(notificationList);

        // when
        var result = mockMvc.perform(get("/users/you"));

        // then
        result.andExpect(status().isOk())
                .andExpect(model().attribute("user", user))
                .andExpect(model().attribute("rankingPosition", 1))
                .andExpect(model().attribute("notifications", notificationList));
    }

    @Test
    @WithMockUser
    void whenUserExistsReturnUser() throws Exception {
        // given
        var user = UserDummy.defaultUser();
        given(userService.getUserById(user.getUserId())).willReturn(user);
        given(rankingService.getPositionOfPlayer(user.getRanking())).willReturn(1);

        // when
        var result = mockMvc.perform(get("/users/u/{id}", user.getUserId()));

        // then
        result.andExpect(status().isOk())
                .andExpect(model().attribute("user", user))
                .andExpect(model().attribute("rankingPosition", 1));
    }

}
