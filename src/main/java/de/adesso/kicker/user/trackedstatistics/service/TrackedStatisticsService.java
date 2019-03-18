package de.adesso.kicker.user.trackedstatistics.service;

import de.adesso.kicker.user.persistence.User;
import de.adesso.kicker.user.service.UserService;
import de.adesso.kicker.user.trackedstatistics.persistence.TrackedStatistics;
import de.adesso.kicker.user.trackedstatistics.persistence.TrackedStatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TrackedStatisticsService {

    private final UserService userService;

    private final TrackedStatisticsRepository trackedStatisticsRepository;

    private final Logger logger = LoggerFactory.getLogger(TrackedStatisticsService.class);

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    void trackStatistics() {
        var users = userService.getAllUsersWithStatistics();
        var trackedStatistics = users.stream().map(this::newTrackedStatistic).collect(Collectors.toList());
        trackedStatisticsRepository.saveAll(trackedStatistics);
        logger.info("Tracked statistics of {} users", users.size());
    }

    private TrackedStatistics newTrackedStatistic(User player) {
        var statistics = player.getStatistics();
        var rank = statistics.getRank();
        var rating = statistics.getRating();
        var wins = statistics.getWins();
        var losses = statistics.getLosses();
        return new TrackedStatistics(rank, rating, wins, losses, player);
    }

    public List<TrackedStatistics> getTrackedStatisticsByUser(User user) {
        return trackedStatisticsRepository.findAllByUser(user);
    }
}
