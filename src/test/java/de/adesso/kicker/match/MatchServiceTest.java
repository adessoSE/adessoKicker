package de.adesso.kicker.match;

import de.adesso.kicker.match.exception.FutureDateException;
import de.adesso.kicker.match.exception.InvalidCreatorException;
import de.adesso.kicker.match.exception.MatchNotFoundException;
import de.adesso.kicker.match.exception.SamePlayerException;
import de.adesso.kicker.user.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MatchServiceTest {

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private MatchService matchService;

    static Stream<Match> createMatchesWithSamePlayers() {
        return Stream.of(MatchDummy.matchWithEqualPlayerA1B1(), MatchDummy.matchWithEqualPlayerA1B2(),
                MatchDummy.matchWithEqualPlayerA2B2(), MatchDummy.matchWithSamePlayerTeamA(),
                MatchDummy.matchWithEqualPlayerTeamB());
    }

    static Match createMatchWithDifferentCreator() {
        return MatchDummy.matchWithoutDefaultUserAsPlayerA1();
    }

    static Match createMatchWithFutureDate() {
        return MatchDummy.matchWithFutureDate();
    }

    static Match createMatch() {
        return MatchDummy.match();
    }

    static List<Match> createMatchList() {
        return Collections.singletonList(MatchDummy.match());
    }

    @BeforeAll
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("If the match is valid it should be created")
    void whenValidMatchThenMatchShouldBeCreated() {
        // given
        var match = createMatch();
        when(userService.getLoggedInUser()).thenReturn(match.getTeamAPlayer1());
        when(matchRepository.save(match)).thenReturn(match);

        // when
        matchService.addMatchEntry(match);

        // then
        verify(matchRepository, times(1)).save(match);
    }

    @Test
    @DisplayName("Match should be verified")
    void matchShouldBeVerified() {
        // given
        var match = createMatch();

        // when
        matchService.verifyMatch(match);

        // then
        assertTrue(match.isVerified());
        verify(matchRepository, times(1)).save(match);
    }

    @ParameterizedTest
    @MethodSource("createMatchesWithSamePlayers")
    @DisplayName("Throw SamePlayerException if a player appears multiple times in a match")
    void whenPlayersAreSameThenThrowSamePlayerException(Match match) {
        // given
        // See method source

        // when
        Executable when = () -> matchService.addMatchEntry(match);

        // then
        Assertions.assertThrows(SamePlayerException.class, when);
    }

    @Test
    @DisplayName("When teamAPlayer1 is not equal to the logged in user a InvalidCreatorExeception should be thrown")
    void whenPlayerA1NotCurrentUserThenThrowInvalidCreatorException() {
        // given
        var match = createMatchWithDifferentCreator();

        // when
        Executable when = () -> matchService.addMatchEntry(match);

        // then
        Assertions.assertThrows(InvalidCreatorException.class, when);
    }

    @Test
    @DisplayName("When match date is in the future FutureDateException should be thrown")
    void whenMatchDateInFutureThrowFutureMatchException() {
        // given
        var match = createMatchWithFutureDate();

        // when
        Executable when = () -> matchService.addMatchEntry(match);

        // then
        Assertions.assertThrows(FutureDateException.class, when);
    }

    @Test
    @DisplayName("If a match with the given id exists it should be returned")
    void whenMatchExistsThenMatchShouldBeFound() {
        // given
        var expected = createMatch();
        when(matchRepository.findById(expected.getMatchId())).thenReturn(Optional.of(expected));

        // when
        var actual = matchService.getMatchById(expected.getMatchId());

        // then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("If there is no match with the given id the MatchNotFoundException should be thrown")
    void whenMatchNotExistentThenThrowMatchNotFoundException() {
        // given
        when(matchRepository.findById(anyString())).thenReturn(Optional.empty());
        var invalidId = "non-existend-id";

        // when
        Executable when = () -> matchService.getMatchById(invalidId);

        // then
        Assertions.assertThrows(MatchNotFoundException.class, when);
    }

    @Test
    @DisplayName("Should return all created matches")
    void shouldReturnAllMatches() {
        // given
        var expected = createMatchList();
        when(matchRepository.findAll()).thenReturn(expected);

        // when
        var actual = matchService.getAllMatches();

        // then
        assertEquals(expected, actual);
    }
}