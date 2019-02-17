package de.adesso.kicker.match;

import de.adesso.kicker.match.exception.MatchNotFoundException;
import de.adesso.kicker.user.User;
import de.adesso.kicker.user.UserDummy;
import de.adesso.kicker.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MatchController.class)
class MatchControllerTest {

    @MockBean
    MatchService matchService;

    @MockBean
    UserService userService;

    @Autowired
    private MockMvc mockMvc;

    private MatchDummy matchDummy = new MatchDummy();
    private UserDummy userDummy = new UserDummy();

    private Match match = matchDummy.match();
    private Match matchNoDate = matchDummy.match_without_date();
    private User user = userDummy.defaultUser();

    private List<Match> matchList = Collections.singletonList(match);
    private List<User> userList = Collections.singletonList(user);

    @BeforeEach
    void setUp() {
        when(userService.getAllUsers()).thenReturn(userList);
    }

    @Test
    void whenMatchNotExistingThenReturn404() throws Exception {
        when(matchService.getMatchById(anyString())).thenThrow(MatchNotFoundException.class);
        this.mockMvc.perform(get("/matches/m/{id}", "non-existent-id")).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    void whenMatchExistsThenReturnPage() throws Exception {
        when(matchService.getMatchById(match.getMatchId())).thenReturn(match);
        this.mockMvc.perform(get("/matches/m/{id}", match.getMatchId())).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("match/page.html"));
    }

    @Test
    void whenMatchesExistThenReturnMatches() throws Exception {
        when(matchService.getAllMatches()).thenReturn(matchList);
        this.mockMvc.perform(get("/matches")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("match/matches.html")).andExpect(model().attribute("matches", matchList))
                .andReturn();
    }

    @Test
    void whenNoMatchesExistThenReturnEmptyList() throws Exception {
        when(matchService.getAllMatches()).thenReturn(new ArrayList<>());
        this.mockMvc.perform(get("/matches")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("match/matches.html")).andExpect(model().attribute("matches", new ArrayList<>()))
                .andReturn();
    }

    @Test
    void getAddMatch() throws Exception {
        this.mockMvc.perform(get("/matches/add")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("match/add.html")).andExpect(model().attribute("match", new Match()))
                .andExpect(model().attribute("users", userList)).andExpect(model().attribute("currentUser", user));
    }

    @Test
    void whenMatchWithOutDateThenReturnNoDate() throws Exception {
        this.mockMvc.perform(post("/matches/add")
                .param("teamAPlayer1", "czarnecki")
                .param("teamBPlayer1", "schneider")
                .param("winnerTeamA", String.valueOf(match.getWinnerTeamA()))
                .param("verifies", String.valueOf(false)))
                .andDo(print());
    }

}