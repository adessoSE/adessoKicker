package de.adesso.kicker.ranking.service;

import de.adesso.kicker.match.persistence.Match;
import de.adesso.kicker.ranking.persistence.*;
import de.adesso.kicker.user.persistence.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankingService {

    private final RankingRepository rankingRepository;

    private static final int MAGNITUDE = 10;

    private static final int RATING_DIFFERENCE_MAGNITUDE = 400;

    @Autowired
    public RankingService(RankingRepository rankingRepository) {
        this.rankingRepository = rankingRepository;
    }

    public void updateRatings(Match match) {
        var winners = match.getWinners();
        var losers = match.getLosers();
        var expectedScore = expectedScore(match);
        var expectedWinnerScore = expectedScore.getFirst();
        var expectedLoserScore = expectedScore.getSecond();
        setNewRatings(winners, Outcome.WON.getScore(), expectedWinnerScore);
        setNewRatings(losers, Outcome.LOST.getScore(), expectedLoserScore);
    }

    public int getPositionOfPlayer(Ranking ranking) {
        return rankingRepository.countAllByRatingAfter(ranking.getRating()) + 1;
    }

    private void setNewRatings(List<User> players, int actualScore, double expectedScore) {
        for (var player : players) {
            var rating = player.getRanking().getRating();
            var kFactor = kFactor(rating);
            rating += kFactor * (actualScore - expectedScore);
            player.getRanking().setRating(rating);
        }
    }

    private int kFactor(int rating) {
        if (rating >= RatingRange.VERY_HIGH.getRating()) {
            return KFactor.LOW.getValue();
        }
        if (rating >= RatingRange.HIGH.getRating()) {
            return KFactor.MEDIUM.getValue();
        }
        return KFactor.HIGH.getValue();
    }

    private Pair<Double, Double> expectedScore(Match match) {
        var ratings = getRatings(match);
        var winnerRating = ratings.getFirst();
        var loserRating = ratings.getSecond();
        var relativeWinnerRating = relativeRating(winnerRating);
        var relativeLoserRating = relativeRating(loserRating);
        var expectedScoreWinner = relativeWinnerRating / (relativeWinnerRating + relativeLoserRating);
        var expectedScoreLoser = relativeLoserRating / (relativeWinnerRating + relativeLoserRating);
        return Pair.of(expectedScoreWinner, expectedScoreLoser);
    }

    private double relativeRating(double rating) {
        return Math.pow(MAGNITUDE, rating / RATING_DIFFERENCE_MAGNITUDE);
    }

    private Pair<Double, Double> getRatings(Match match) {
        var winners = match.getWinners();
        var losers = match.getLosers();
        var loserRating = losers.stream().map(User::getRanking).mapToInt(Ranking::getRating).average().getAsDouble();
        var winnerRating = winners.stream().map(User::getRanking).mapToInt(Ranking::getRating).average().getAsDouble();
        return Pair.of(winnerRating, loserRating);
    }
}
