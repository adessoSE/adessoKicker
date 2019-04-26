package de.adesso.kicker.match.controller;

import de.adesso.kicker.match.exception.FutureDateException;
import de.adesso.kicker.match.exception.InvalidCreatorException;
import de.adesso.kicker.match.exception.SamePlayerException;
import de.adesso.kicker.match.persistence.Match;
import de.adesso.kicker.match.service.MatchService;
import de.adesso.kicker.notification.message.persistence.MessageDummy;
import de.adesso.kicker.notification.persistence.Notification;
import de.adesso.kicker.notification.service.NotificationService;
import de.adesso.kicker.user.persistence.User;
import de.adesso.kicker.user.persistence.UserDummy;
import de.adesso.kicker.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.keycloak.adapters.springboot.KeycloakAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource("classpath:application-test.properties")
@Import(KeycloakAutoConfiguration.class)
@WebMvcTest(value = MatchController.class)
class MatchControllerTest {

    @MockBean
    MatchService matchService;

    @MockBean(name = "userService")
    UserService userService;

    @MockBean(name = "notificationService")
    NotificationService notificationService;

    @Autowired
    private MockMvc mockMvc;

    private static User createUser() {
        return UserDummy.defaultUser();
    }

    private static List<User> createUserList() {
        return Arrays.asList(UserDummy.defaultUser(), UserDummy.alternateUser());
    }

    @Test
    @DisplayName("Return 'match/add.html' and contain a new Match")
    @WithMockUser
    void getAddMatch() throws Exception {
        // given
        var user = createUser();
        var userList = createUserList();
        List<Notification> notificationList = Collections.singletonList(MessageDummy.messageDeclined());
        given(userService.getAllUsers()).willReturn(userList);
        given(userService.getLoggedInUser()).willReturn(user);
        given(notificationService.getNotificationsByReceiver(any(User.class))).willReturn(notificationList);

        // when
        var result = this.mockMvc.perform(get("/matches/add"));

        // then
        result.andExpect(status().isOk())
                .andExpect(view().name("sites/matchresult.html"))
                .andExpect(model().attribute("match", new Match()))
                .andExpect(model().attribute("currentUser", user))
                .andExpect(model().attribute("users", userList));
    }

    @Test
    @DisplayName("When no date entered then 'noDate' should exist")
    @WithMockUser
    void whenMatchWithOutDateThenReturnNoDate() throws Exception {
        // given
        var user = createUser();
        var userList = createUserList();
        given(userService.getAllUsers()).willReturn(userList);
        given(userService.getLoggedInUser()).willReturn(user);

        // when
        var result = this.mockMvc.perform(post("/matches/add").param("teamAPlayer1.userId", "user")
                .with(csrf())
                .param("teamBPlayer1.userId", "user2")
                .param("winnerTeamA", "true"));

        // then
        result.andExpect(status().isOk())
                .andExpect(model().attributeExists("noDate"))
                .andExpect(model().attribute("currentUser", user))
                .andExpect(model().attribute("users", userList));
        then(matchService).shouldHaveZeroInteractions();
    }

    @Test
    @DisplayName("When no winner has been selected 'noWinner' should exist")
    @WithMockUser
    void whenMatchWithNoWinnerThenReturnNoWinner() throws Exception {
        // given
        var user = createUser();
        var userList = createUserList();
        given(userService.getAllUsers()).willReturn(userList);
        given(userService.getLoggedInUser()).willReturn(user);

        // when
        var result = mockMvc.perform(post("/matches/add").param("date", LocalDate.now().toString())
                .with(csrf())
                .param("teamAPlayer1.userId", "user")
                .param("teamBPlayer1.userId", "user2"));

        // then
        result.andExpect(status().isOk())
                .andExpect(model().attributeExists("noWinner"))
                .andExpect(model().attribute("currentUser", user))
                .andExpect(model().attribute("users", userList));
        then(matchService).shouldHaveZeroInteractions();
    }

    @Test
    @DisplayName("When no players have been selected 'nullPlayer' should exist")
    @WithMockUser
    void whenMatchWithNoPlayersThenReturnNullPlayers() throws Exception {
        // given
        var user = createUser();
        var userList = createUserList();
        when(userService.getAllUsers()).thenReturn(userList);
        when(userService.getLoggedInUser()).thenReturn(user);

        // when
        var result = mockMvc.perform(post("/matches/add").with(csrf())
                .param("date", LocalDate.now().toString())
                .param("winnerTeamA", "true"));

        // then
        result.andExpect(status().isOk())
                .andExpect(model().attributeExists("nullPlayer"))
                .andExpect(model().attribute("currentUser", user))
                .andExpect(model().attribute("users", userList));
        then(matchService).shouldHaveZeroInteractions();
    }

    @Test
    @DisplayName("When only 'teamAPlayer1' has been selected 'nullPlayer' should exist")
    @WithMockUser
    void whenMatchWithNoPlayerB1ThenReturnNullPlayers() throws Exception {
        // given
        var user = createUser();
        var userList = createUserList();
        when(userService.getAllUsers()).thenReturn(userList);
        when(userService.getLoggedInUser()).thenReturn(user);

        // when
        var result = mockMvc.perform(post("/matches/add").param("date", LocalDate.now().toString())
                .with(csrf())
                .param("teamAPlayer1.userId", "user")
                .param("winnerTeamA", "true"));

        // then
        result.andExpect(status().isOk())
                .andExpect(model().attributeExists("nullPlayer"))
                .andExpect(model().attribute("currentUser", user))
                .andExpect(model().attribute("users", userList));
        then(matchService).shouldHaveZeroInteractions();
    }

    @Test
    @DisplayName("When only 'teamBPlayer1' has been selected 'nullPlayer' should exist")
    @WithMockUser
    void whenMatchWithNoPlayerA1ThenReturnNullPlayers() throws Exception {
        // given
        var user = createUser();
        var userList = createUserList();
        when(userService.getAllUsers()).thenReturn(userList);
        when(userService.getLoggedInUser()).thenReturn(user);

        // when
        var result = mockMvc.perform(post("/matches/add").param("date", LocalDate.now().toString())
                .with(csrf())
                .param("teamBPlayer1.userId", "user2")
                .param("winnerTeamA", "true"));

        // then
        result.andExpect(status().isOk())
                .andExpect(model().attributeExists("nullPlayer"))
                .andExpect(model().attribute("currentUser", user))
                .andExpect(model().attribute("users", userList));
        then(matchService).shouldHaveZeroInteractions();
    }

    @Test
    @DisplayName("When a valid match is entered expect 'successMessage' and called service")
    @WithMockUser
    void whenMatchValidThenReturnSuccessMessage() throws Exception {
        // given
        var user = createUser();
        var userList = createUserList();
        given(userService.getAllUsers()).willReturn(userList);
        given(userService.getLoggedInUser()).willReturn(user);

        // when
        var result = mockMvc.perform(post("/matches/add").param("date", LocalDate.now().toString())
                .with(csrf())
                .param("teamAPlayer1.userId", "user")
                .param("teamBPlayer1.userId", "user2")
                .param("winnerTeamA", "true"));

        // then
        result.andExpect(status().isOk())
                .andExpect(model().attributeExists("successMessage"))
                .andExpect(model().attribute("currentUser", user))
                .andExpect(model().attribute("users", userList));
        then(matchService).should(times(1)).addMatchEntry(any(Match.class));
    }

    @Test
    @DisplayName("When 'FutureDateException' is thrown expect 'futureDate'")
    @WithMockUser
    void expectFutureDate() throws Exception {
        // given
        var user = createUser();
        var userList = createUserList();
        given(userService.getAllUsers()).willReturn(userList);
        given(userService.getLoggedInUser()).willReturn(user);
        willThrow(FutureDateException.class).given(matchService).addMatchEntry(any(Match.class));

        // when
        var result = mockMvc.perform(post("/matches/add").param("date", LocalDate.now().plusDays(1).toString())
                .with(csrf())
                .param("teamAPlayer1.userId", "user")
                .param("teamBPlayer1.userId", "user2")
                .param("winnerTeamA", "true"));

        // then
        result.andExpect(status().isOk())
                .andExpect(model().attributeExists("futureDate"))
                .andExpect(model().attribute("currentUser", user))
                .andExpect(model().attribute("users", userList));
        then(matchService).should(times(1)).addMatchEntry(any(Match.class));
    }

    @Test
    @DisplayName("When 'InvalidCreatorException' is thrown expect 'invalidCreator'")
    @WithMockUser
    void expectInvalidCreator() throws Exception {
        // given
        var user = createUser();
        var userList = createUserList();
        given(userService.getAllUsers()).willReturn(userList);
        given(userService.getLoggedInUser()).willReturn(user);
        willThrow(InvalidCreatorException.class).given(matchService).addMatchEntry(any(Match.class));

        // when
        var result = mockMvc.perform(post("/matches/add").param("date", LocalDate.now().plusDays(1).toString())
                .with(csrf())
                .param("teamAPlayer1.userId", "user")
                .param("teamBPlayer1.userId", "user2")
                .param("winnerTeamA", "true"));

        // then
        result.andExpect(status().isOk())
                .andExpect(model().attributeExists("invalidCreator"))
                .andExpect(model().attribute("currentUser", user))
                .andExpect(model().attribute("users", userList));
        then(matchService).should(times(1)).addMatchEntry(any(Match.class));
    }

    @Test
    @DisplayName("When 'SamePlayerException' is thrown expect 'samePlayer'")
    @WithMockUser
    void expectSamePlayer() throws Exception {
        // given
        var user = createUser();
        var userList = createUserList();
        given(userService.getAllUsers()).willReturn(userList);
        given(userService.getLoggedInUser()).willReturn(user);
        doThrow(SamePlayerException.class).when(matchService).addMatchEntry(any(Match.class));

        // when
        var result = mockMvc.perform(post("/matches/add").param("date", LocalDate.now().plusDays(1).toString())
                .with(csrf())
                .param("teamAPlayer1.userId", "user")
                .param("teamBPlayer1.userId", "user2")
                .param("winnerTeamA", "true"));

        // then
        result.andExpect(status().isOk())
                .andExpect(model().attributeExists("samePlayer"))
                .andExpect(model().attribute("currentUser", user))
                .andExpect(model().attribute("users", userList));
        then(matchService).should(times(1)).addMatchEntry(any(Match.class));
    }
}