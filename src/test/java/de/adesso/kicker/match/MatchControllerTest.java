package de.adesso.kicker.match;

import de.adesso.kicker.user.User;
import de.adesso.kicker.user.UserDummy;
import de.adesso.kicker.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = MatchController.class, secure = false)
class MatchControllerTest {

    @MockBean
    MatchService matchService;

    @MockBean
    UserService userService;

    @Autowired
    private MockMvc mockMvc;

    private static List<User> createUserList() {
        return Arrays.asList(UserDummy.defaultUser(), UserDummy.alternateUser());
    }

    @Test
    @DisplayName("Return 'match/add.html' and contain a new Match")
    @WithMockUser
    void getAddMatch() throws Exception {
        // given
        var userList = createUserList();
        when(userService.getAllUsers()).thenReturn(userList);

        // when
        var result = this.mockMvc.perform(get("/matches/add"));

        // then
        result.andExpect(status().isOk())
                .andExpect(view().name("match/add.html"))
                .andExpect(model().attribute("match", new Match()))
                .andExpect(model().attribute("users", userList));
    }

    @Test
    @DisplayName("When no date entered then 'noDate' should exist")
    @WithMockUser
    void whenMatchWithOutDateThenReturnNoDate() throws Exception {
        // given
        var userList = createUserList();
        when(userService.getAllUsers()).thenReturn(userList);

        // when
        var result = this.mockMvc.perform(post("/matches/add").param("teamAPlayer1", "user")
                .param("teamBPlayer1", "user2")
                .param("winnerTeamA", "true"));

        // then
        result.andExpect(model().attributeExists("noDate"));
    }

    @Test
    @DisplayName("When no winner has been selected 'noWinner' should exist")
    @WithMockUser
    void whenMatchWithNoWinnerThenReturnNoWinner() throws Exception {
        // given
        var userList = createUserList();
        when(userService.getAllUsers()).thenReturn(userList);

        // when
        var result = mockMvc.perform(post("/matches/add").param("date", LocalDate.now().toString())
                .param("teamAPlayer1", "user")
                .param("teamBPlayer1", "user2"));

        // then
        result.andExpect(model().attributeExists("noWinner"));
    }

    @Test
    @WithMockUser
    void whenMatchWithNoPlayersThenReturnNullPlayers() throws Exception {
        // given
        var userList = createUserList();
        when(userService.getAllUsers()).thenReturn(userList);

        // when
        var result = mockMvc
                .perform(post("/matches/add").param("date", LocalDate.now().toString()).param("winnerTeamA", "true"));

        // then
        result.andExpect(model().attributeExists("nullPlayer"));
    }

}