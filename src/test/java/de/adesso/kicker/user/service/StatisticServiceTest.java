package de.adesso.kicker.user.service;

import de.adesso.kicker.match.persistence.MatchDummy;
import de.adesso.kicker.user.persistence.Statistic;
import de.adesso.kicker.user.persistence.StatisticDummy;
import de.adesso.kicker.user.persistence.StatisticRepository;
import de.adesso.kicker.user.persistence.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.any;

@TestPropertySource("classpath:application-test.properties")
class StatisticServiceTest {

    @Mock
    private StatisticRepository statisticRepository;

    @InjectMocks
    private StatisticsService statisticsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    static List<Statistic> createStatisticsList() {
        return List.of(StatisticDummy.statistic(), StatisticDummy.statisticHighRating(),
                StatisticDummy.statisticVeryHighRating());
    }

    @Test
    @DisplayName("When players with a rating of 1500 play their rating should change by 16")
    void ratingsShouldChangeBy16() {
        // given
        var match = MatchDummy.matchWithLowRating();

        // when
        statisticsService.updateStatistics(match.getWinners(), match.getLosers());

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
        statisticsService.updateStatistics(match.getWinners(), match.getLosers());

        // then
        assertRatingPlayers(1262, match.getWinners());
        assertRatingPlayers(1238, match.getLosers());
    }

    @Test
    @DisplayName("When players with a rating of 2400 play their rating should change by 8")
    void ratingsShouldChangeBy8() {
        // given
        var match = MatchDummy.matchWithVeryHighRating();

        // when
        statisticsService.updateStatistics(match.getWinners(), match.getLosers());

        // then
        assertRatingPlayers(1508, match.getWinners());
        assertRatingPlayers(1492, match.getLosers());
    }

    @Test
    @DisplayName("When to players in different rating ranges play each players respective k Factor")
    void shouldUseEachPlayersKFactor() {
        // given
        var match = MatchDummy.matchWithPlayersInDifferentRatingRanges();
        given(statisticRepository.save(any(Statistic.class))).willReturn(new Statistic());

        // when
        statisticsService.updateStatistics(match.getWinners(), match.getLosers());

        // then
        assertRatingPlayers(1030, match.getWinners());
        assertRatingPlayers(1485, match.getLosers());
    }

    @Test
    void assertNewStatisticsAreSaved() {
        // given
        var rankingList = createStatisticsList();
        given(statisticRepository.findAll()).willReturn(rankingList);
        given(statisticRepository.countAllByRatingAfter(anyInt())).willReturn(0);

        // when
        statisticsService.updateRanks();

        // then
        then(statisticRepository).should().saveAll(rankingList);
    }

    @Test
    void assertRankIsCorrect() {
        // given
        var ranking = StatisticDummy.statistic();
        given(statisticRepository.findAll()).willReturn(List.of(ranking));
        given(statisticRepository.countAllByRatingAfter(anyInt())).willReturn(0);

        // when
        statisticsService.updateRanks();

        // then
        assertEquals(1, ranking.getRank());
    }

    private static void assertRatingPlayers(int expected, List<User> players) {
        for (var player : players) {
            var actual = player.getStatistic().getRating();
            assertEquals(expected, actual);
        }
    }
}