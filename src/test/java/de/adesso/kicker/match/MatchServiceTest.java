package de.adesso.kicker.match;

import de.adesso.kicker.match.exception.InvalidCreatorException;
import de.adesso.kicker.match.exception.MatchNotFoundException;
import de.adesso.kicker.match.exception.SamePlayerException;
import de.adesso.kicker.user.User;
import de.adesso.kicker.user.UserDummy;
import de.adesso.kicker.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MatchServiceTest {

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private MatchService matchService;

    private MatchDummy matchDummy = new MatchDummy();
    private UserDummy userDummy = new UserDummy();

    private Match match = matchDummy.match();
    private Match match1_1 = matchDummy.match_with_equal_player1();
    private Match match1_2 = matchDummy.match_with_equal_player1_2();
    private Match match1 = matchDummy.match_with_same_player_team();
    private Match match2 = matchDummy.match_with_equal_player2_2();
    private Match match_without_default_user = matchDummy.match_without_default_user_player_1();
    private User user = userDummy.defaultUser();

    private List<Match> matchList = Collections.singletonList(match);

    @BeforeAll
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(matchRepository.save(any(Match.class))).thenAnswer((Answer<Match>) invocation -> {
            Object[] args = invocation.getArguments();
            return (Match) args[0];
        });
        when(userService.getLoggedInUser()).thenReturn(user);
        when(matchRepository.findByMatchId(anyString())).thenReturn(null);
        when(matchRepository.findByMatchId(match.getMatchId())).thenReturn(match);
        when(matchRepository.findAll()).thenReturn(matchList);
    }

    @Test
    void test_addMatchEntry() {
        var testMatch = matchService.addMatchEntry(match);
        assertEquals(match, testMatch);
    }

    @Test
    void test_verifyMatch() {
        matchService.verifyMatch(match);
        assertTrue(match.isVerified());
    }

    @Test
    void test_addMatchEntry_SamePlayerException() {
        Assertions.assertThrows(SamePlayerException.class, () -> matchService.addMatchEntry(match1_1));
        Assertions.assertThrows(SamePlayerException.class, () -> matchService.addMatchEntry(match1_2));
        Assertions.assertThrows(SamePlayerException.class, () -> matchService.addMatchEntry(match1));
        Assertions.assertThrows(SamePlayerException.class, () -> matchService.addMatchEntry(match2));
    }

    @Test
    void test_addMatchEntry_InvalidCreatorException() {
        Assertions.assertThrows(InvalidCreatorException.class,
                () -> matchService.addMatchEntry(match_without_default_user));
    }

    @Test
    void test_getMatchById() {
        assertEquals(match, matchService.getMatchById(match.getMatchId()));
    }

    @Test
    void test_getMatchById_MatchNotFoundException() {
        Assertions.assertThrows(MatchNotFoundException.class, () -> matchService.getMatchById("non-existent-id"));
    }

    @Test
    void test_getAllMatches() {
        assertEquals(matchList, matchService.getAllMatches());
    }
}