package de.adesso.kicker.season.persistence;

import java.util.List;

public class SeasonDummy {
    public static Season season(List<SeasonMatch> seasonMatches, List<SeasonTrackedStatistic> seasonTrackedStatistics) {
        return new Season("Season 1", seasonMatches, seasonTrackedStatistics);
    }
}
