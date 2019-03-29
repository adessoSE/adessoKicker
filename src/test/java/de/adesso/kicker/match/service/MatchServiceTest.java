package de.adesso.kicker.match.service;

import de.adesso.kicker.events.MatchDeclinedEventDummy;
import de.adesso.kicker.events.MatchVerifiedEventDummy;
import de.adesso.kicker.match.exception.FutureDateException;
import de.adesso.kicker.match.exception.InvalidCreatorException;
import de.adesso.kicker.match.exception.SamePlayerException;
import de.adesso.kicker.match.persistence.Match;
import de.adesso.kicker.match.persistence.MatchDummy;
import de.adesso.kicker.match.persistence.MatchRepository;
import de.adesso.kicker.match.service.events.MatchCreatedEvent;
import de.adesso.kicker.user.service.StatisticsService;
import de.adesso.kicker.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.TestPropertySource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;

@TestPropertySource("classpath:application-test.properties")
class MatchServiceTest {

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private UserService userService;

    @Mock
    private StatisticsService statisticsService;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @InjectMocks
    private MatchService matchService;

    private static Stream<Match> createMatchesWithSamePlayers() {
        return Stream.of(MatchDummy.matchWithEqualPlayerA1B1(), MatchDummy.matchWithEqualPlayerA1B2(),
                MatchDummy.matchWithEqualPlayerA2B2(), MatchDummy.matchWithSamePlayerTeamA(),
                MatchDummy.matchWithEqualPlayerTeamB());
    }

    private static Match createMatchWithDifferentCreator() {
        return MatchDummy.matchWithoutDefaultUserAsPlayerA1();
    }

    private static Match createMatchWithFutureDate() {
        return MatchDummy.matchWithFutureDate();
    }

    private static Match createMatch() {
        return MatchDummy.match();
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("If the match is valid it should be created and a MatchCreatedEvent should be published")
    void whenValidMatchThenMatchShouldBeCreatedAndEventPublished() {
        // given
        var match = createMatch();
        given(userService.getLoggedInUser()).willReturn(match.getTeamAPlayer1());
        given(matchRepository.save(match)).willReturn(match);

        // when
        matchService.addMatchEntry(match);

        // then
        then(matchRepository).should(times(1)).save(match);
        then(applicationEventPublisher).should(times(1)).publishEvent(any(MatchCreatedEvent.class));
    }

    @Test
    @DisplayName("Match should be deleted if declined")
    void matchShouldBeDeleted() {
        // given
        var matchDeclinedEvent = MatchDeclinedEventDummy.matchDeclinedEvent();
        var match = matchDeclinedEvent.getMatch();

        // when
        matchService.declineMatch(matchDeclinedEvent);

        // then
        then(matchRepository).should(times(1)).delete(match);
    }

    @Test
    @DisplayName("Match should be verified")
    void matchShouldBeVerified() {
        // given
        var matchVerifiedEvent = MatchVerifiedEventDummy.matchVerifiedEvent();
        var match = matchVerifiedEvent.getMatch();

        // when
        matchService.verifyMatch(matchVerifiedEvent);

        // then
        assertTrue(match.isVerified());
        then(matchRepository).should(times(1)).save(match);
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
        assertThrows(SamePlayerException.class, when);
    }

    @Test
    @DisplayName("When teamAPlayer1 is not equal to the logged in user a InvalidCreatorException should be thrown")
    void whenPlayerA1NotCurrentUserThenThrowInvalidCreatorException() {
        // given
        var match = createMatchWithDifferentCreator();

        // when
        Executable when = () -> matchService.addMatchEntry(match);

        // then
        assertThrows(InvalidCreatorException.class, when);
    }

    @Test
    @DisplayName("When match date is in the future FutureDateException should be thrown")
    void whenMatchDateInFutureThrowFutureMatchException() {
        // given
        var match = createMatchWithFutureDate();

        // when
        Executable when = () -> matchService.addMatchEntry(match);

        // then
        assertThrows(FutureDateException.class, when);
    }
}