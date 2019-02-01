package de.adesso.kicker.match;

import de.adesso.kicker.match.exception.SamePlayerMatchExeption;
import de.adesso.kicker.match.exception.SamePlayerTeamException;
import de.adesso.kicker.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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
    private Match match = matchDummy.match();
    private Match match1_1 = matchDummy.match_with_equal_player1();
    private Match match1_2 = matchDummy.match_with_equal_player1_2();
    private Match match1 = matchDummy.match_with_same_player_team();

    @BeforeAll
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(matchRepository.save(any(Match.class))).thenAnswer((Answer<Match>) invocation -> {
            Object[] args = invocation.getArguments();
            return (Match) args[0];
        });
    }

    @Test
    void test_addMatchEntry() {
        var testMatch = matchService.addMatchEntry(match);
        assertEquals(match, testMatch);
    }

    @Test
    void test_addMatchEntry_SamePlayerMatchException() {
        Assertions.assertThrows(SamePlayerMatchExeption.class, () -> matchService.addMatchEntry(match1_1));
        Assertions.assertThrows(SamePlayerMatchExeption.class, () -> matchService.addMatchEntry(match1_2));
    }

    @Test
    void test_addMatchEntry_SamePlayerTeamException() {
        Assertions.assertThrows(SamePlayerTeamException.class, () -> matchService.addMatchEntry(match1));
    }
}