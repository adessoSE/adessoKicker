package de.adesso.kicker.user;

import de.adesso.kicker.user.persistence.Statistic;

public class StatisticsDummy {
    public static Statistic statistic() {
        return new Statistic();
    }

    public static Statistic statisticHighRating() {
        var statistics = new Statistic();
        statistics.setRating(2100);
        return statistics;
    }

    public static Statistic statisticVeryHighRating() {
        var statistics = new Statistic();
        statistics.setRating(2400);
        return statistics;
    }
}
