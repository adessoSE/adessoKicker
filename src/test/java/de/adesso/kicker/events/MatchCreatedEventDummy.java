package de.adesso.kicker.events;

import de.adesso.kicker.match.MatchDummy;

public class MatchCreatedEventDummy {

    public static MatchCreatedEvent matchCreatedEvent() {
        var match = MatchDummy.match();
        var matchCreatedEvent = new MatchCreatedEvent(new Object(), match);
        return matchCreatedEvent;
    }
}
