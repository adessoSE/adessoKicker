package de.adesso.kicker.ranking;

import de.adesso.kicker.match.MatchDummy;
import de.adesso.kicker.ranking.persistence.Ranking;
import de.adesso.kicker.ranking.persistence.RankingRepository;
import de.adesso.kicker.ranking.service.RankingService;
import de.adesso.kicker.user.UserDummy;
import de.adesso.kicker.user.persistence.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class RankingServiceTest {

    @Mock
    private RankingRepository rankingRepository;

    @InjectMocks
    private RankingService rankingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    static User createUser() {
        return UserDummy.defaultUser();
    }

    @Test
    @DisplayName("When players with a rating of 1500 play their rating should change by 16")
    void ratingsShouldChangeBy16() {
        // given
        var match = MatchDummy.matchWithLowRating();

        // when
        rankingService.updateRatings(match);

        // then
        assertRatingPlayers(1016, match.getWinners());
        assertRatingPlayers(984, match.getLosers());
    }

    @Test
    @DisplayName("When players with a rating of 2100 play their rating should change by 12")
    void ratingsShouldChangeBy12() {
        // given
        var match = MatchDummy.matchWithHighRating();

        // when
        rankingService.updateRatings(match);

        // then
        assertRatingPlayers(2112, match.getWinners());
        assertRatingPlayers(2088, match.getLosers());
    }

    @Test
    @DisplayName("When players with a rating of 2400 play their rating should change by 8")
    void ratingsShouldChangeBy8() {
        // given
        var match = MatchDummy.matchWithVeryHighRating();

        // when
        rankingService.updateRatings(match);

        // then
        assertRatingPlayers(2408, match.getWinners());
        assertRatingPlayers(2392, match.getLosers());
    }

    @Test
    @DisplayName("When to players in different rating ranges play each players respective k Factor")
    void shouldUseEachPlayersKFactor() {
        // given
        var match = MatchDummy.matchWithPlayersInDifferentRatingRanges();
        when(rankingRepository.save(any(Ranking.class))).thenReturn(new Ranking());

        // when
        rankingService.updateRatings(match);

        // then
        assertRatingPlayers(1032, match.getWinners());
        assertRatingPlayers(2384, match.getLosers());
    }

    @Test
    @DisplayName("Should return position of user plus one")
    void shouldReturnPositionPlusOne() {
        // given
        var user = createUser();
        given(rankingRepository.countAllByRatingAfter(user.getRanking().getRating())).willReturn(0);

        // when
        var actualRank = rankingService.getPositionOfPlayer(user.getRanking());

        // then
        assertEquals(1, actualRank);
    }

    private static void assertRatingPlayers(int expected, List<User> players) {
        for (var player : players) {
            var actual = player.getRanking().getRating();
            assertEquals(expected, actual);
        }
    }
}