package de.adesso.kicker.user.trackedstatistics.entity;

import de.adesso.kicker.user.persistence.User;
import de.adesso.kicker.user.trackedstatistic.persistence.TrackedStatistic;

public class TrackedStatisticsDummy {
    public static TrackedStatistic trackedStatistic(User user) {
        return new TrackedStatistic(1, 1016, 1L, 0L, user);
    }
}
