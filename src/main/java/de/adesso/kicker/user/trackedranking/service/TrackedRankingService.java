package de.adesso.kicker.user.trackedranking.service;

import de.adesso.kicker.user.persistence.User;
import de.adesso.kicker.user.service.UserService;
import de.adesso.kicker.user.trackedranking.persistence.TrackedRanking;
import de.adesso.kicker.user.trackedranking.persistence.TrackedRankingRepository;
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
class TrackedRankingService {

    private final UserService userService;

    private final TrackedRankingRepository trackedRankingRepository;

    private final Logger logger = LoggerFactory.getLogger(TrackedRankingService.class);

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    void trackRankings() {
        var users = userService.getAllUsersWithRank();
        var trackedRankings = users.stream().map(user -> {
            var ranking = user.getRanking();
            var rank = ranking.getRank();
            var rating = ranking.getRating();
            return new TrackedRanking(rank, rating, user);
        }).collect(Collectors.toList());
        trackedRankingRepository.saveAll(trackedRankings);
        logger.info("Tracked rankings of {} users", users.size());
    }

    public List<TrackedRanking> getTrackedRankingsByUser(User user) {
        return trackedRankingRepository.findAllByUser(user);
    }
}
