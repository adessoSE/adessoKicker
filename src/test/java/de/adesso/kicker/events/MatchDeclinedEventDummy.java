package de.adesso.kicker.events;

import de.adesso.kicker.events.match.MatchDeclinedEvent;
import de.adesso.kicker.match.MatchDummy;

public class MatchDeclinedEventDummy {

    public static MatchDeclinedEvent matchDeclinedEvent() {
        var match = MatchDummy.match();
        return new MatchDeclinedEvent(new Object(), match);
    }
}
