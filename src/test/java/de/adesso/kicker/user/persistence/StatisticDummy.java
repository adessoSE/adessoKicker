package de.adesso.kicker.user.persistence;

public class StatisticDummy {
    public static Statistic statistic() {
        return new Statistic();
    }

    public static Statistic statisticHighRating() {
        var statistics = new Statistic();
        statistics.setRating(1250);
        return statistics;
    }

    public static Statistic statisticVeryHighRating() {
        var statistics = new Statistic();
        statistics.setRating(1500);
        return statistics;
    }
}
