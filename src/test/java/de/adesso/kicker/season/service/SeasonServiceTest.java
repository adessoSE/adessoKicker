package de.adesso.kicker.season.service;

import de.adesso.kicker.match.persistence.MatchDummy;
import de.adesso.kicker.match.service.MatchService;
import de.adesso.kicker.season.persistence.SeasonDummy;
import de.adesso.kicker.season.persistence.SeasonMatchDummy;
import de.adesso.kicker.season.persistence.SeasonRepository;
import de.adesso.kicker.season.persistence.SeasonTrackedStatisticDummy;
import de.adesso.kicker.user.persistence.UserDummy;
import de.adesso.kicker.user.service.UserService;
import de.adesso.kicker.user.trackedstatistic.service.TrackedStatisticService;
import de.adesso.kicker.user.trackedstatistics.entity.TrackedStatisticsDummy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@TestPropertySource("classpath:application-test.properties")
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

    @Test
    void verifySeasonIsEndedCorrectly() {
        // given
        var match = MatchDummy.match();
        var matches = List.of(match);
        var user = UserDummy.userWithLowRating();
        var trackedStatistic = TrackedStatisticsDummy.trackedStatistic(user);
        var seasonTrackedStatistic = SeasonTrackedStatisticDummy.seasonTrackedStatistic(trackedStatistic);
        var seasonMatch = SeasonMatchDummy.seasonMatch(match);
        var trackedStatistics = List.of(trackedStatistic);

        var expectedSeason = SeasonDummy.season(List.of(seasonMatch), List.of(seasonTrackedStatistic));

        given(seasonRepository.count()).willReturn(0L);
        given(matchService.getAllVerifiedMatches()).willReturn(matches);
        given(trackedStatisticService.getAllTrackedStatistics()).willReturn(trackedStatistics);
        willDoNothing().given(matchService).deleteAll();
        willDoNothing().given(userService).deleteAllStatistics();
        willDoNothing().given(eventPublisher).publishEvent(any());

        // when
        seasonService.finishSeason();

        // then
        then(seasonRepository).should().count();
        then(matchService).should().getAllVerifiedMatches();
        then(trackedStatisticService).should().getAllTrackedStatistics();
        then(seasonRepository).should().save(expectedSeason);
        then(matchService).should().deleteAll();
        then(userService).should().deleteAllStatistics();
        then(eventPublisher).should().publishEvent(any());
    }
}