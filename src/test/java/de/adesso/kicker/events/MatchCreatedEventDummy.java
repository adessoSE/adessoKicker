package de.adesso.kicker.events;

import de.adesso.kicker.match.persistence.Match;
import de.adesso.kicker.match.service.events.MatchCreatedEvent;

public class MatchCreatedEventDummy {

    public static MatchCreatedEvent matchCreatedEvent(Match match) {
        return new MatchCreatedEvent(new Object(), match);
    }
}
