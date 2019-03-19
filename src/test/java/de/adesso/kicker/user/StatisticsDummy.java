package de.adesso.kicker.user;

import de.adesso.kicker.user.persistence.Statistics;

public class StatisticsDummy {
    public static Statistics statistic() {
        return new Statistics();
    }

    public static Statistics statisticHighRating() {
        var statistics = new Statistics();
        statistics.setRating(2100);
        return statistics;
    }

    public static Statistics statisticVeryHighRating() {
        var statistics = new Statistics();
        statistics.setRating(2400);
        return statistics;
    }
}
