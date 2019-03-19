package de.adesso.kicker.season.service;

import de.adesso.kicker.match.MatchDummy;
import de.adesso.kicker.match.persistence.Match;
import de.adesso.kicker.match.service.MatchService;
import de.adesso.kicker.season.persistence.*;
import de.adesso.kicker.user.UserDummy;
import de.adesso.kicker.user.persistence.User;
import de.adesso.kicker.user.service.UserService;
import de.adesso.kicker.user.trackedstatistic.persistence.TrackedStatistic;
import de.adesso.kicker.user.trackedstatistic.service.TrackedStatisticService;
import de.adesso.kicker.user.trackedstatistics.TrackedStatisticsDummy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

class SeasonServiceTest {

    @Mock
    private SeasonRepository seasonRepository;

    @Mock
    private UserService userService;

    @Mock
    private TrackedStatisticService trackedStatisticService;

    @Mock
    private MatchService matchService;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private SeasonService seasonService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    private static Season expectedSeason(Match match, User user, TrackedStatistic trackedStatistics) {
        var seasonMatches = List.of(seasonMatches(match));
        var seasonTrackedStatistic = List.of(seasonTrackedStatistic(trackedStatistics));
        var seasonStatistics = List.of(seasonStatistic(user, seasonTrackedStatistic));
        return new Season("Season 1", seasonMatches, seasonStatistics);
    }

    private static SeasonMatch seasonMatches(Match match) {
        return SeasonMatch.builder()
                .teamAPlayer1(match.getTeamAPlayer1())
                .teamBPlayer1(match.getTeamBPlayer1())
                .teamAPlayer2(match.getTeamAPlayer2())
                .teamBPlayer2(match.getTeamBPlayer2())
                .date(match.getDate())
                .winnerTeamA(match.getWinnerTeamA())
                .build();
    }

    private static SeasonTrackedStatistic seasonTrackedStatistic(TrackedStatistic trackedStatistics) {
        return SeasonTrackedStatistic.builder()
                .date(trackedStatistics.getDate())
                .rank(trackedStatistics.getRank())
                .rating(trackedStatistics.getRating())
                .wins(trackedStatistics.getWins())
                .losses(trackedStatistics.getLosses())
                .build();
    }

    private static SeasonStatistic seasonStatistic(User user, List<SeasonTrackedStatistic> seasonTrackedStatistics) {
        var statistic = user.getStatistic();
        return SeasonStatistic.builder()
                .user(user)
                .rank(statistic.getRank())
                .rating(statistic.getRating())
                .wins(statistic.getWins())
                .losses(statistic.getLosses())
                .seasonTrackedStatistics(seasonTrackedStatistics)
                .build();
    }

    @Test
    void finishSeason() {
        // given
        var match = MatchDummy.match();
        var user = UserDummy.userWithLowRating();
        var trackedStatistic = TrackedStatisticsDummy.trackedStatistic(user);
        var matches = List.of(match);
        var users = List.of(user);
        var trackedStatistics = List.of(trackedStatistic);

        var expectedSeason = expectedSeason(match, user, trackedStatistic);

        given(seasonRepository.count()).willReturn(0L);
        given(matchService.getAllVerifiedMatches()).willReturn(matches);
        given(userService.getAllUsersWithStatistics()).willReturn(users);
        given(trackedStatisticService.getTrackedStatisticsByUser(any())).willReturn(trackedStatistics);
        willDoNothing().given(matchService).deleteAll();
        willDoNothing().given(userService).deleteAllStatistics();
        willDoNothing().given(eventPublisher).publishEvent(any());

        // when
        seasonService.finishSeason();

        // then
        then(seasonRepository).should().count();
        then(matchService).should().getAllVerifiedMatches();
        then(userService).should().getAllUsersWithStatistics();
        then(trackedStatisticService).should().getTrackedStatisticsByUser(any());
        then(seasonRepository).should().save(expectedSeason);
        then(matchService).should().deleteAll();
        then(userService).should().deleteAllStatistics();
        then(eventPublisher).should().publishEvent(any());
    }
}