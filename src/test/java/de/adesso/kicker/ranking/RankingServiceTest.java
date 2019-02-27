package de.adesso.kicker.ranking;

import de.adesso.kicker.events.MatchVerifiedEventDummy;
import de.adesso.kicker.ranking.persistence.Ranking;
import de.adesso.kicker.ranking.persistence.RankingRepository;
import de.adesso.kicker.ranking.service.RankingService;
import de.adesso.kicker.user.persistence.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RankingServiceTest {

    @Mock
    private RankingRepository rankingRepository;

    @InjectMocks
    private RankingService rankingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("When players with a rating of 1500 play their rating should change by 16")
    void ratingsShouldChangeBy16() {
        // given
        var matchVerifiedEvent = MatchVerifiedEventDummy.matchVerifiedEventLowRanking();
        var match = matchVerifiedEvent.getMatch();

        // when
        rankingService.updateRatings(matchVerifiedEvent);

        // then
        assertRating(match.getWinners(), 1516);
        assertRating(match.getLosers(), 1484);
    }

    @Test
    @DisplayName("When players with a rating of 2100 play their rating should change by 12")
    void ratingsShouldChangeBy12() {
        // given
        var matchVerifiedEvent = MatchVerifiedEventDummy.matchVerifiedEventHighRanking();
        var match = matchVerifiedEvent.getMatch();

        // when
        rankingService.updateRatings(matchVerifiedEvent);

        // then
        assertRating(match.getWinners(), 2112);
        assertRating(match.getLosers(), 2088);
    }

    @Test
    @DisplayName("When players with a rating of 2400 play their rating should change by 8")
    void ratingsShouldChangeBy8() {
        // given
        var matchVerifiedEvent = MatchVerifiedEventDummy.matchVerifiedEventVeryHighRanking();
        var match = matchVerifiedEvent.getMatch();

        // when
        rankingService.updateRatings(matchVerifiedEvent);

        // then
        assertRating(match.getWinners(), 2408);
        assertRating(match.getLosers(), 2392);
    }

    @Test
    @DisplayName("When to players in different rating ranges play each players respective k Factor")
    void shouldUseEachPlayersKFactor() {
        // given
        var matchVerifiedEvent = MatchVerifiedEventDummy.matchVerifiedEventPlayersDifferentRankingRanges();
        var match = matchVerifiedEvent.getMatch();
        when(rankingRepository.save(any(Ranking.class))).thenReturn(new Ranking());

        // when
        rankingService.updateRatings(matchVerifiedEvent);

        // then
        assertRating(match.getWinners(), 2402);
        assertRating(match.getLosers(), 2096);
    }

    @Test
    void verifyAllRatingsAreUpdated() {
        // given
        var matchVerifiedEvent = MatchVerifiedEventDummy.matchVerifiedEvent();

        // when
        rankingService.updateRatings(matchVerifiedEvent);

        // then
        verify(rankingRepository, times(2)).saveAll(any(List.class));
    }

    private static void assertRating(List<User> players, int expected) {
        for (var player : players) {
            var actual = player.getRanking().getRating();
            assertEquals(expected, actual);
        }
    }
}