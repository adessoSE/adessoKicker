package de.adesso.kicker.events;

import de.adesso.kicker.match.persistence.MatchDummy;
import de.adesso.kicker.match.service.events.MatchDeclinedEvent;

public class MatchDeclinedEventDummy {

    public static MatchDeclinedEvent matchDeclinedEvent() {
        var match = MatchDummy.match();
        return new MatchDeclinedEvent(new Object(), match);
    }
}
