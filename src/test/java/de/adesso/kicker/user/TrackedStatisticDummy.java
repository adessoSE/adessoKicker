package de.adesso.kicker.user;

import de.adesso.kicker.user.trackedstatistic.persistence.TrackedStatistic;

public class TrackedStatisticDummy {

    public static TrackedStatistic trackedStatistic() { return new TrackedStatistic();}

    public static TrackedStatistic trackedStatisticWithVeryHighRating() {
        var trackedStatistic = new TrackedStatistic(1, 2000, 30L, 3L, null);
        return trackedStatistic;
    }
}
