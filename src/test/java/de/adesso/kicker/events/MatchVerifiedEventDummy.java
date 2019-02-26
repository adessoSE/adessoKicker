package de.adesso.kicker.events;

import de.adesso.kicker.match.MatchDummy;

public class MatchVerifiedEventDummy {

    public static MatchVerifiedEvent matchVerifiedEvent() {
        var match = MatchDummy.match();
        var matchVerifiedEvent = new MatchVerifiedEvent(new Object(), match);
        return matchVerifiedEvent;
    }

    public static MatchVerifiedEvent matchVerifiedEventLowRanking() {
        var match = MatchDummy.matchWithLowRating();
        var matchVerifiedEvent = new MatchVerifiedEvent(new Object(), match);
        return matchVerifiedEvent;
    }

    public static MatchVerifiedEvent matchVerifiedEventHighRanking() {
        var match = MatchDummy.matchWithHighRating();
        var matchVerifiedEvent = new MatchVerifiedEvent(new Object(), match);
        return matchVerifiedEvent;
    }

    public static MatchVerifiedEvent matchVerifiedEventVeryHighRanking() {
        var match = MatchDummy.matchWithVeryHighRating();
        var matchVerifiedEvent = new MatchVerifiedEvent(new Object(), match);
        return matchVerifiedEvent;
    }

    public static MatchVerifiedEvent matchVerifiedEventPlayersDifferentRankingRanges() {
        var match = MatchDummy.matchWithPlayersInDifferentRatingRanges();
        var matchVerifiedEvent = new MatchVerifiedEvent(new Object(), match);
        return matchVerifiedEvent;
    }
}
