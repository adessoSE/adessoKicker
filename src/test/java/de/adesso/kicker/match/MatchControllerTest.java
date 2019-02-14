package de.adesso.kicker.match;

import de.adesso.kicker.match.exception.InvalidCreatorException;
import de.adesso.kicker.match.exception.MatchNotFoundException;
import de.adesso.kicker.match.exception.SamePlayerException;
import de.adesso.kicker.user.User;
import de.adesso.kicker.user.UserDummy;
import de.adesso.kicker.user.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MatchControllerTest {

    @Mock
    MatchService matchService;

    @Mock
    UserService userService;

    @InjectMocks
    MatchController matchController;

    private MatchDummy matchDummy = new MatchDummy();
    private UserDummy userDummy = new UserDummy();

    private Match match = matchDummy.match();
    private Match match_noDate = matchDummy.match_without_date();
    private Match match_nullPlayers = matchDummy.match_without_players();
    private Match match_onlyA1 = matchDummy.match_with_only_player_a_1();
    private Match match_onlyB1 = matchDummy.match_with_only_player_b_1();
    private Match match_noWinner = matchDummy.match_without_winner();
    private Match match_samePlayers = matchDummy.match_with_equal_player1();
    private Match match_invalidCreator = matchDummy.match_without_default_user_player_1();
    private User user = userDummy.defaultUser();

    private List<Match> matchList = Collections.singletonList(match);
    private List<User> userList = Collections.singletonList(user);

    @BeforeAll
    void setUp() {
        MockitoAnnotations.initMocks(this);

        when(matchService.getMatchById(match.getMatchId())).thenReturn(match);
        when(matchService.getMatchById(anyString())).thenThrow(MatchNotFoundException.class);
        when(matchService.getAllMatches()).thenReturn(matchList);
        when(matchService.addMatchEntry(match_samePlayers)).thenThrow(SamePlayerException.class);
        when(matchService.addMatchEntry(match_invalidCreator)).thenThrow(InvalidCreatorException.class);
        when(userService.getLoggedInUser()).thenReturn(user);
        when(userService.getAllUsers()).thenReturn(userList);
    }

    @Test
    void test_getAllMatches() {
        var actual_modelAndView = matchController.getAllMatches();
        var expected_modelAndView = new ModelAndView();
        expected_modelAndView.addObject("user", user);
        expected_modelAndView.addObject("matches", matchList);
        expected_modelAndView.setViewName("match/matches.html");
        assertModelAndView(expected_modelAndView, actual_modelAndView);
    }

    @Test
    void test_getMatch() {
        var actual_modelAndView = matchController.getMatch(match.getMatchId());
        var expected_modelAndView = new ModelAndView();
        expected_modelAndView.addObject("user", user);
        expected_modelAndView.addObject("match", match);
        expected_modelAndView.setViewName("match/page.html");
        assertModelAndView(expected_modelAndView, actual_modelAndView);
    }

    @Test
    void test_getMatch_NonExistentMatch() {
        var actual_modelAndView = matchController.getMatch("non-existent-id");
        var expected_modelAndView = new ModelAndView();
        expected_modelAndView.setViewName("error/404.html");
        assertModelAndView(expected_modelAndView, actual_modelAndView);
    }

    @Test
    void test_getAddMatch() {
        var actual_modelAndView = matchController.getAddMatch();
        var expected_modelAndView = new ModelAndView();
        expected_modelAndView.addObject("match", new Match());
        expected_modelAndView.addObject("users", userList);
        expected_modelAndView.addObject("currentUser", user);
        expected_modelAndView.setViewName("match/add.html");
        assertModelAndView(expected_modelAndView, actual_modelAndView);
    }

    @Test
    void test_postAddMatch() {
        var actual_modelAndView = matchController.postAddMatch(match);
        var expected_modelAndView = new ModelAndView();
        expected_modelAndView.addObject("successMessage", true);
        expected_modelAndView.addObject("match", new Match());
        expected_modelAndView.addObject("users", userList);
        expected_modelAndView.addObject("currentUser", user);
        expected_modelAndView.setViewName("match/add.html");
        assertModelAndView(actual_modelAndView, expected_modelAndView);
    }

    @Test
    void test_postAddMatch_noDate() {
        var actual_modelAndView = matchController.postAddMatch(match_noDate);
        var expected_modelAndView = new ModelAndView();
        expected_modelAndView.addObject("noDate", true);
        expected_modelAndView.addObject("match", new Match());
        expected_modelAndView.addObject("users", userList);
        expected_modelAndView.addObject("currentUser", user);
        expected_modelAndView.setViewName("match/add.html");
        assertModelAndView(actual_modelAndView, expected_modelAndView);
    }

    @Test
    void test_postAddMatch_nullPlayers() {
        var actual_modelAndView_withBothNull = matchController.postAddMatch(match_nullPlayers);
        var actual_modelAndView_withOnlyA1 = matchController.postAddMatch(match_onlyA1);
        var actual_modelAndView_withOnlyB1 = matchController.postAddMatch(match_onlyB1);
        var expected_modelAndView = new ModelAndView();
        expected_modelAndView.addObject("nullPlayer", true);
        expected_modelAndView.addObject("match", new Match());
        expected_modelAndView.addObject("users", userList);
        expected_modelAndView.addObject("currentUser", user);
        expected_modelAndView.setViewName("match/add.html");
        assertModelAndView(actual_modelAndView_withBothNull, expected_modelAndView);
        assertModelAndView(actual_modelAndView_withOnlyA1, expected_modelAndView);
        assertModelAndView(actual_modelAndView_withOnlyB1, expected_modelAndView);
    }

    @Test
    void test_postAddMatch_noWinner() {
        var actual_modelAndView = matchController.postAddMatch(match_noWinner);
        var expected_modelAndView = new ModelAndView();
        expected_modelAndView.addObject("noWinner", true);
        expected_modelAndView.addObject("match", new Match());
        expected_modelAndView.addObject("users", userList);
        expected_modelAndView.addObject("currentUser", user);
        expected_modelAndView.setViewName("match/add.html");
        assertModelAndView(expected_modelAndView, actual_modelAndView);
    }

    @Test
    void test_postAddMatch_invalidCreator() {
        var actual_modelAndView = matchController.postAddMatch(match_invalidCreator);
        var expected_modelAndView = new ModelAndView();
        expected_modelAndView.addObject("invalidCreator", true);
        expected_modelAndView.addObject("match", new Match());
        expected_modelAndView.addObject("users", userList);
        expected_modelAndView.addObject("currentUser", user);
        expected_modelAndView.setViewName("match/add.html");
        assertModelAndView(expected_modelAndView, actual_modelAndView);
    }

    @Test
    void test_postAddMatch_samePlayer() {
        var actual_modelAndView = matchController.postAddMatch(match_samePlayers);
        var expected_modelAndView = new ModelAndView();
        expected_modelAndView.addObject("samePlayer", true);
        expected_modelAndView.addObject("match", new Match());
        expected_modelAndView.addObject("users", userList);
        expected_modelAndView.addObject("currentUser", user);
        expected_modelAndView.setViewName("match/add.html");
        assertModelAndView(expected_modelAndView, actual_modelAndView);
    }

    private void assertModelAndView(ModelAndView expected, ModelAndView actual) {
        assertEquals(expected.getModelMap(), actual.getModelMap());
        assertEquals(expected.getView(), actual.getView());
    }
}