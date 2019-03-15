package de.adesso.kicker.user.service;

import de.adesso.kicker.user.persistence.Ranking;
import de.adesso.kicker.user.persistence.RankingRepository;
import de.adesso.kicker.user.persistence.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final RankingRepository rankingRepository;

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

    public void updateRanks() {
        var rankings = getAllRankings();
        rankings.forEach(ranking -> {
            int rank = calculateRank(ranking);
            ranking.setRank(rank);
        });
        saveAllRankings(rankings);
    }

    private int calculateRank(Ranking ranking) {
        return rankingRepository.countAllByRatingAfter(ranking.getRating()) + INDEX_OFFSET;
    }

    public void updateRatings(List<User> winners, List<User> losers) {
        var winnerRating = getTeamRating(winners);
        var loserRating = getTeamRating(losers);

        var expectedWinnerScore = expectedScore(winnerRating, loserRating);
        var expectedLoserScore = ONE_HUNDRED_PERCENT - expectedWinnerScore;

        applyResultsToTeam(winners, Outcome.WON, expectedWinnerScore);
        applyResultsToTeam(losers, Outcome.LOST, expectedLoserScore);

        updateRanks();
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
        return players.stream().map(user -> {
            var ranking = user.getRanking();
            if (Objects.isNull(ranking)) {
                ranking = new Ranking();
                user.setRanking(ranking);
            }
            return ranking;
        }).mapToInt(Ranking::getRating).sum();
    }

    private void applyResultsToTeam(List<User> players, Outcome outcome, double expectedScore) {
        for (var player : players) {
            var ranking = player.getRanking();
            var rating = ranking.getRating();
            var kFactor = kFactor(rating);
            var change = ratingChange(kFactor, outcome, expectedScore);
            applyRatingChange(ranking, change);
        }
    }

    private long ratingChange(KFactor kFactor, Outcome outcome, double expectedScore) {
        return Math.round(kFactor.getValue() * (outcome.getScore() - expectedScore));
    }

    private void applyRatingChange(Ranking ranking, long change) {
        ranking.updateRating(change);
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

    private List<Ranking> getAllRankings() {
        return rankingRepository.findAll();
    }

    private void saveAllRankings(List<Ranking> rankings) {
        rankingRepository.saveAll(rankings);
    }
}
