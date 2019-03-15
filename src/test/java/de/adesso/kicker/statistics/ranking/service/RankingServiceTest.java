package de.adesso.kicker.statistics.ranking.service;

import de.adesso.kicker.match.MatchDummy;
import de.adesso.kicker.statistics.ranking.RankingDummy;
import de.adesso.kicker.user.persistence.Ranking;
import de.adesso.kicker.user.persistence.RankingRepository;
import de.adesso.kicker.user.persistence.User;
import de.adesso.kicker.user.service.RankingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.any;

class RankingServiceTest {

    @Mock
    private RankingRepository rankingRepository;

    @InjectMocks
    private RankingService rankingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    static List<Ranking> createRankingList() {
        return List.of(RankingDummy.ranking(), RankingDummy.highRating(), RankingDummy.veryHighRating());
    }

    @Test
    @DisplayName("When players with a rating of 1500 play their rating should change by 16")
    void ratingsShouldChangeBy16() {
        // given
        var match = MatchDummy.matchWithLowRating();

        // when
        rankingService.updateRatings(match.getWinners(), match.getLosers());

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
        rankingService.updateRatings(match.getWinners(), match.getLosers());

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
        rankingService.updateRatings(match.getWinners(), match.getLosers());

        // then
        assertRatingPlayers(2408, match.getWinners());
        assertRatingPlayers(2392, match.getLosers());
    }

    @Test
    @DisplayName("When to players in different rating ranges play each players respective k Factor")
    void shouldUseEachPlayersKFactor() {
        // given
        var match = MatchDummy.matchWithPlayersInDifferentRatingRanges();
        given(rankingRepository.save(any(Ranking.class))).willReturn(new Ranking());

        // when
        rankingService.updateRatings(match.getWinners(), match.getLosers());

        // then
        assertRatingPlayers(1032, match.getWinners());
        assertRatingPlayers(2384, match.getLosers());
    }

    @Test
    void assertNewRankingsAreSaved() {
        // given
        var rankingList = createRankingList();
        given(rankingRepository.findAll()).willReturn(rankingList);
        given(rankingRepository.countAllByRatingAfter(anyInt())).willReturn(0);

        // when
        rankingService.updateRanks();

        // then
        then(rankingRepository).should().saveAll(rankingList);
    }

    @Test
    void assertRankIsCorrect() {
        // given
        var ranking = RankingDummy.ranking();
        given(rankingRepository.findAll()).willReturn(List.of(ranking));
        given(rankingRepository.countAllByRatingAfter(anyInt())).willReturn(0);

        // when
        rankingService.updateRanks();

        // then
        assertEquals(1, ranking.getRank());
    }

    private static void assertRatingPlayers(int expected, List<User> players) {
        for (var player : players) {
            var actual = player.getRanking().getRating();
            assertEquals(expected, actual);
        }
    }
}