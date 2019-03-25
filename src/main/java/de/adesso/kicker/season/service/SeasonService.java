package de.adesso.kicker.season.service;

import de.adesso.kicker.match.persistence.Match;
import de.adesso.kicker.match.service.MatchService;
import de.adesso.kicker.season.persistence.Season;
import de.adesso.kicker.season.persistence.SeasonMatch;
import de.adesso.kicker.season.persistence.SeasonRepository;
import de.adesso.kicker.season.persistence.SeasonTrackedStatistic;
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
        sendSeasonEndedEvent(seasonName);
    }

    private String getSeasonName() {
        var seasonNumber = seasonRepository.count() + SEASON_OFFSET;
        return String.format("Season %d", seasonNumber);
    }

    private void backUpOldSeason(String seasonName) {
        var seasonMatches = copyAllMatches();
        var seasonStatistics = copyStatisticsForUser();

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

    private void sendSeasonEndedEvent(String seasonName) {
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

    private List<SeasonTrackedStatistic> copyStatisticsForUser() {
        var trackedStatistics = trackedStatisticService.getAllTrackedStatistics();
        return trackedStatistics.stream().map(this::copyTrackedStatistic).collect(Collectors.toList());
    }

    private SeasonTrackedStatistic copyTrackedStatistic(TrackedStatistic trackedStatistic) {
        var user = trackedStatistic.getUser();
        var rank = trackedStatistic.getRank();
        var rating = trackedStatistic.getRating();
        var wins = trackedStatistic.getWins();
        var losses = trackedStatistic.getLosses();
        var data = trackedStatistic.getDate();

        return SeasonTrackedStatistic.builder()
                .user(user)
                .rank(rank)
                .rating(rating)
                .wins(wins)
                .losses(losses)
                .date(data)
                .build();
    }
}
