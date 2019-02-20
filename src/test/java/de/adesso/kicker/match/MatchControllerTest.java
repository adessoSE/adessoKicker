package de.adesso.kicker.match;

import de.adesso.kicker.match.exception.MatchNotFoundException;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
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

    static List<User> createUserList() {
        UserDummy userDummy = new UserDummy();
        return Arrays.asList(userDummy.defaultUser(), userDummy.alternateUser());
    }

    static Match createMatch() {
        MatchDummy matchDummy = new MatchDummy();
        return matchDummy.match();
    }

    static List<Match> createMatchList() {
        MatchDummy matchDummy = new MatchDummy();
        return Collections.singletonList(matchDummy.match());
    }

    @Test
    @DisplayName("When a match doesn't exist status code 404 should be returned")
    @WithMockUser
    void whenMatchNotExistingThenReturn404() throws Exception {
        when(matchService.getMatchById(anyString())).thenThrow(MatchNotFoundException.class);

        this.mockMvc.perform(get("/matches/m/{id}", "non-existent-id")).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("When a match exists it's page should be returned")
    @WithMockUser
    void whenMatchExistsThenReturnPage() throws Exception {
        var match = createMatch();
        when(matchService.getMatchById(match.getMatchId())).thenReturn(match);

        this.mockMvc.perform(get("/matches/m/{id}", match.getMatchId()))
                .andExpect(status().isOk())
                .andExpect(view().name("match/page.html"));
    }

    @Test
    @DisplayName("When matches exist they should be shown")
    @WithMockUser
    void whenMatchesExistThenReturnMatches() throws Exception {
        var matchList = createMatchList();
        when(matchService.getAllMatches()).thenReturn(matchList);

        this.mockMvc.perform(get("/matches"))
                .andExpect(status().isOk())
                .andExpect(view().name("match/matches.html"))
                .andExpect(model().attribute("matches", matchList))
                .andReturn();
    }

    @Test
    @DisplayName("When no matches exist an empty list should be shown")
    @WithMockUser
    void whenNoMatchesExistThenReturnEmptyList() throws Exception {
        when(matchService.getAllMatches()).thenReturn(Collections.emptyList());

        this.mockMvc.perform(get("/matches"))
                .andExpect(status().isOk())
                .andExpect(view().name("match/matches.html"))
                .andExpect(model().attribute("matches", new ArrayList<>()))
                .andReturn();
    }

    @Test
    @DisplayName("Return 'match/add.html' and contain a new Match")
    @WithMockUser
    void getAddMatch() throws Exception {
        var userList = createUserList();
        when(userService.getAllUsers()).thenReturn(userList);

        this.mockMvc.perform(get("/matches/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("match/add.html"))
                .andExpect(model().attribute("match", new Match()))
                .andExpect(model().attribute("users", userList));
    }

    @Test
    @DisplayName("When no date entered then 'noDate' should exist")
    @WithMockUser
    void whenMatchWithOutDateThenReturnNoDate() throws Exception {
        var userList = createUserList();
        when(userService.getAllUsers()).thenReturn(userList);

        this.mockMvc.perform(post("/matches/add").param("teamAPlayer1", "user")
                .param("teamBPlayer1", "user2")
                .param("winnerTeamA", "true")).andExpect(model().attributeExists("noDate"));
    }

    @Test
    @DisplayName("When no winner has been selected 'noWinner' should exist")
    @WithMockUser
    void whenMatchWithNoWinnerThenReturnNoWinner() throws Exception {
        var userList = createUserList();
        when(userService.getAllUsers()).thenReturn(userList);

        mockMvc.perform(post("/matches/add").param("date", LocalDate.now().toString())
                .param("teamAPlayer1", "user")
                .param("teamBPlayer1", "user2")).andExpect(model().attributeExists("noWinner"));
    }

    @Test
    @WithMockUser
    void whenMatchWithNoPlayersThenReturnNullPlayers() throws Exception {
        var userList = createUserList();
        when(userService.getAllUsers()).thenReturn(userList);

        mockMvc.perform(post("/matches/add").param("date", LocalDate.now().toString()).param("winnerTeamA", "true"))
                .andExpect(model().attributeExists("nullPlayer"));
    }

}