package de.adesso.kicker.season.persistence;

import de.adesso.kicker.user.trackedstatistic.persistence.TrackedStatistic;

public class SeasonTrackedStatisticDummy {
    public static SeasonTrackedStatistic seasonTrackedStatistic(TrackedStatistic trackedStatistic) {
        return SeasonTrackedStatistic.builder()
                .date(trackedStatistic.getDate())
                .rating(trackedStatistic.getRating())
                .rank(trackedStatistic.getRank())
                .losses(trackedStatistic.getLosses())
                .wins(trackedStatistic.getWins())
                .user(trackedStatistic.getUser())
                .build();
    }
}
