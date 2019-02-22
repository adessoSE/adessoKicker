package de.adesso.kicker.ranking;

import de.adesso.kicker.match.Match;
import de.adesso.kicker.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        changeRating(winners, Outcome.WON.getScore(), expectedWinnerScore);
        changeRating(losers, Outcome.LOST.getScore(), expectedLoserScore);
    }

    private void changeRating(List<User> players, int actualScore, double expectedScore) {
        for (var player : players) {
            var rating = player.getRanking().getRating();
            var kFactor = kFactor(rating);
            rating += kFactor * (actualScore - expectedScore);
            player.getRanking().setRating(rating);
        }
        var ratings = players.stream().map(User::getRanking).collect(Collectors.toList());
        saveRatings(ratings);
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

    private void saveRatings(List<Ranking> rankings) {
        rankingRepository.saveAll(rankings);
    }
}
