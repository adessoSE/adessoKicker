package de.adesso.kicker.events;

import de.adesso.kicker.events.match.MatchVerifiedEvent;
import de.adesso.kicker.match.MatchDummy;

public class MatchVerifiedEventDummy {

    public static MatchVerifiedEvent matchVerifiedEvent() {
        var match = MatchDummy.match();
        return new MatchVerifiedEvent(new Object(), match);
    }

    public static MatchVerifiedEvent matchVerifiedEventLowRanking() {
        var match = MatchDummy.matchWithLowRating();
        return new MatchVerifiedEvent(new Object(), match);
    }

    public static MatchVerifiedEvent matchVerifiedEventHighRanking() {
        var match = MatchDummy.matchWithHighRating();
        return new MatchVerifiedEvent(new Object(), match);
    }

    public static MatchVerifiedEvent matchVerifiedEventVeryHighRanking() {
        var match = MatchDummy.matchWithVeryHighRating();
        return new MatchVerifiedEvent(new Object(), match);
    }

    public static MatchVerifiedEvent matchVerifiedEventPlayersDifferentRankingRanges() {
        var match = MatchDummy.matchWithPlayersInDifferentRatingRanges();
        return new MatchVerifiedEvent(new Object(), match);
    }
}
