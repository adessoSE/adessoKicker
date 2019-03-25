package de.adesso.kicker.user.trackedstatistic.service;

import de.adesso.kicker.user.persistence.User;
import de.adesso.kicker.user.service.UserService;
import de.adesso.kicker.user.trackedstatistic.persistence.TrackedStatistic;
import de.adesso.kicker.user.trackedstatistic.persistence.TrackedStatisticRepository;
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
public class TrackedStatisticService {

    private final UserService userService;

    private final TrackedStatisticRepository trackedStatisticRepository;

    private final Logger logger = LoggerFactory.getLogger(TrackedStatisticService.class);

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    void trackStatistics() {
        var users = userService.getAllUsersWithStatistics();
        var trackedStatistics = users.stream().map(this::newTrackedStatistic).collect(Collectors.toList());
        trackedStatisticRepository.saveAll(trackedStatistics);
        logger.info("Tracked statistic of {} users", users.size());
    }

    private TrackedStatistic newTrackedStatistic(User player) {
        var statistics = player.getStatistic();
        var rank = statistics.getRank();
        var rating = statistics.getRating();
        var wins = statistics.getWins();
        var losses = statistics.getLosses();
        return new TrackedStatistic(rank, rating, wins, losses, player);
    }

    public List<TrackedStatistic> getTrackedStatisticsByUser(User user) {
        return trackedStatisticRepository.findAllByUser(user);
    }

    public List<TrackedStatistic> getAllTrackedStatistics() {
        return trackedStatisticRepository.findAll();
    }
}
