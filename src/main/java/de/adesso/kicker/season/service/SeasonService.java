package de.adesso.kicker.season.service;

import de.adesso.kicker.match.persistence.Match;
import de.adesso.kicker.match.service.MatchService;
import de.adesso.kicker.season.persistence.*;
import de.adesso.kicker.user.persistence.User;
import de.adesso.kicker.user.service.UserService;
import de.adesso.kicker.user.trackedstatistic.persistence.TrackedStatistic;
import de.adesso.kicker.user.trackedstatistic.service.TrackedStatisticService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeasonService {

    private final SeasonRepository seasonRepository;

    private final UserService userService;

    private final TrackedStatisticService trackedStatisticService;

    private final MatchService matchService;

    private final ApplicationEventPublisher eventPublisher;

    private final Logger logger = LoggerFactory.getLogger(SeasonService.class);

    private static final int SEASON_OFFSET = 1;

    @Transactional
    @Scheduled(cron = "0 0 1 1 1,4,7,10 ?")
    void finishSeason() {
        var seasonName = getSeasonName();
        backUpOldSeason(seasonName);
        deleteOldSeason();
        seasonEndedEvent(seasonName);
    }

    private String getSeasonName() {
        var seasonNumber = seasonRepository.count() + SEASON_OFFSET;
        return String.format("Season %d", seasonNumber);
    }

    private void backUpOldSeason(String seasonName) {
        var seasonMatches = copyAllMatches();
        var seasonStatistics = copyAllStatistics();

        var season = new Season(seasonName, seasonMatches, seasonStatistics);
        seasonRepository.save(season);
        logger.info("{} has been saved", seasonName);
    }

    private void deleteOldSeason() {
        matchService.deleteAll();
        logger.info("All matches have been deleted");

        userService.deleteAllStatistics();
        logger.info("All statistics have been deleted");
    }

    private void seasonEndedEvent(String seasonName) {
        var seasonEndedEvent = new SeasonEndedEvent(this, seasonName);
        eventPublisher.publishEvent(seasonEndedEvent);
    }

    private List<SeasonMatch> copyAllMatches() {
        var matches = matchService.getAllVerifiedMatches();
        return matches.stream().map(this::copyMatch).collect(Collectors.toList());
    }

    private SeasonMatch copyMatch(Match match) {
        var teamAPlayer1 = match.getTeamAPlayer1();
        var teamAPlayer2 = match.getTeamAPlayer2();
        var teamBPlayer1 = match.getTeamBPlayer1();
        var teamBPlayer2 = match.getTeamBPlayer2();
        var winnerTeamA = match.getWinnerTeamA();
        var date = match.getDate();

        return SeasonMatch.builder()
                .teamAPlayer1(teamAPlayer1)
                .teamAPlayer2(teamAPlayer2)
                .teamBPlayer1(teamBPlayer1)
                .teamBPlayer2(teamBPlayer2)
                .winnerTeamA(winnerTeamA)
                .date(date)
                .build();
    }

    private List<SeasonStatistic> copyAllStatistics() {
        var users = userService.getAllUsersWithStatistics();
        return users.stream().map(this::copyStatistic).collect(Collectors.toList());
    }

    private SeasonStatistic copyStatistic(User user) {
        var statistic = user.getStatistic();
        var rank = statistic.getRank();
        var rating = statistic.getRating();
        var wins = statistic.getWins();
        var losses = statistic.getLosses();
        var seasonTrackedStatistics = copyStatisticsForUser(user);

        return SeasonStatistic.builder()
                .user(user)
                .rank(rank)
                .rating(rating)
                .wins(wins)
                .losses(losses)
                .seasonTrackedStatistics(seasonTrackedStatistics)
                .build();
    }

    private List<SeasonTrackedStatistic> copyStatisticsForUser(User user) {
        var trackedStatistics = trackedStatisticService.getTrackedStatisticsByUser(user);
        return trackedStatistics.stream().map(this::copyTrackedStatistic).collect(Collectors.toList());
    }

    private SeasonTrackedStatistic copyTrackedStatistic(TrackedStatistic trackedStatistics) {
        var rank = trackedStatistics.getRank();
        var rating = trackedStatistics.getRating();
        var wins = trackedStatistics.getWins();
        var losses = trackedStatistics.getLosses();
        var data = trackedStatistics.getDate();

        return SeasonTrackedStatistic.builder().rank(rank).rating(rating).wins(wins).losses(losses).date(data).build();
    }
}
