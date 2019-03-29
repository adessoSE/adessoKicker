package de.adesso.kicker.user.service;

import de.adesso.kicker.user.persistence.Statistic;
import de.adesso.kicker.user.persistence.StatisticRepository;
import de.adesso.kicker.user.persistence.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final StatisticRepository statisticRepository;

    /**
     * When two opponents have a rating difference of
     * {@value RATING_DIFFERENCE_MAGNITUDE} the expected chance to win differs by a
     * multiple of {@value MAGNITUDE}
     */
    private static final int MAGNITUDE = 10;
    private static final int RATING_DIFFERENCE_MAGNITUDE = 400;

    /**
     * Combined value of both opponents expected score. This means when one has the
     * expected score of one opponent they can subtract expected score from
     * {@value ONE_HUNDRED_PERCENT} and get the other opponents expected score.
     * <p>
     * Opponent 1 has an expected score of 0.4 thus the expected score of Opponent 2
     * is 0.6
     */
    private static final int ONE_HUNDRED_PERCENT = 1;

    private static final int INDEX_OFFSET = 1;

    void updateRanks() {
        var statistics = getAllStatistics();
        statistics.forEach(this::setRanks);
        saveAllStatistics(statistics);
    }

    private void setRanks(Statistic statistic) {
        var rank = calculateRank(statistic);
        statistic.setRank(rank);
    }

    private int calculateRank(Statistic statistic) {
        return statisticRepository.countAllByRatingAfter(statistic.getRating()) + INDEX_OFFSET;
    }

    public void updateStatistics(List<User> winners, List<User> losers) {
        var winnerRating = getTeamRating(winners);
        var loserRating = getTeamRating(losers);

        var expectedWinnerScore = expectedScore(winnerRating, loserRating);
        var expectedLoserScore = ONE_HUNDRED_PERCENT - expectedWinnerScore;

        increaseWins(winners);
        increaseLosses(losers);

        updateRatings(winners, Outcome.WON, expectedWinnerScore);
        updateRatings(losers, Outcome.LOST, expectedLoserScore);

        updateRanks();
    }

    private void increaseWins(List<User> winners) {
        winners.stream().map(User::getStatistic).forEach(Statistic::increaseWins);
    }

    private void increaseLosses(List<User> losers) {
        losers.stream().map(User::getStatistic).forEach(Statistic::increaseLosses);
    }

    private double expectedScore(int winnerRating, int loserRating) {
        var relativeWinnerRating = relativeRating(winnerRating);
        var relativeLoserRating = relativeRating(loserRating);

        return relativeWinnerRating / (relativeWinnerRating + relativeLoserRating);
    }

    private double relativeRating(double rating) {
        return Math.pow(MAGNITUDE, rating / RATING_DIFFERENCE_MAGNITUDE);
    }

    private int getTeamRating(List<User> players) {
        var statistics = new ArrayList<Statistic>();
        for (var player : players) {
            statistics.add(getStatisticOrElseNew(player));
        }
        return statistics.stream().mapToInt(Statistic::getRating).sum();
    }

    private Statistic getStatisticOrElseNew(User user) {
        var statistic = user.getStatistic();
        if (Objects.isNull(statistic)) {
            statistic = new Statistic();
            user.setStatistic(statistic);
        }
        return statistic;
    }

    private void updateRatings(List<User> players, Outcome outcome, double expectedScore) {
        players.stream().map(User::getStatistic).forEach(statistic -> updateRating(statistic, outcome, expectedScore));
    }

    private void updateRating(Statistic statistic, Outcome outcome, double expectedScore) {
        var rating = statistic.getRating();
        var kFactor = kFactor(rating);
        var change = ratingChange(kFactor, outcome, expectedScore);
        applyRatingChange(statistic, change);
    }

    private long ratingChange(KFactor kFactor, Outcome outcome, double expectedScore) {
        return Math.round(kFactor.getValue() * (outcome.getScore() - expectedScore));
    }

    private void applyRatingChange(Statistic statistic, long change) {
        statistic.updateRating(change);
    }

    private KFactor kFactor(int rating) {
        if (rating >= RatingRange.VERY_HIGH.getRating()) {
            return KFactor.LOW;
        }
        if (rating >= RatingRange.HIGH.getRating()) {
            return KFactor.MEDIUM;
        }
        return KFactor.HIGH;
    }

    private List<Statistic> getAllStatistics() {
        return statisticRepository.findAll();
    }

    private void saveAllStatistics(List<Statistic> statistics) {
        statisticRepository.saveAll(statistics);
    }

    void deleteAll() {
        statisticRepository.deleteAll();
    }
}
