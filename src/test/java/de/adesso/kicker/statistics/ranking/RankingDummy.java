package de.adesso.kicker.statistics.ranking;

import de.adesso.kicker.user.persistence.Ranking;

public class RankingDummy {
    public static Ranking ranking() {
        return new Ranking();
    }

    public static Ranking highRating() {
        var ranking = new Ranking();
        ranking.setRating(2100);
        return ranking;
    }

    public static Ranking veryHighRating() {
        var ranking = new Ranking();
        ranking.setRating(2400);
        return ranking;
    }
}
