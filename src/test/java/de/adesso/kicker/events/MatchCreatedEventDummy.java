package de.adesso.kicker.events;

import de.adesso.kicker.events.match.MatchCreatedEvent;
import de.adesso.kicker.match.persistence.Match;

public class MatchCreatedEventDummy {

    public static MatchCreatedEvent matchCreatedEvent(Match match) {
        return new MatchCreatedEvent(new Object(), match);
    }
}
