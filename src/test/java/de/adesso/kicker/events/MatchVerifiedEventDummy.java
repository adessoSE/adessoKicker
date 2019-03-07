package de.adesso.kicker.events;

import de.adesso.kicker.events.match.MatchVerifiedEvent;
import de.adesso.kicker.match.MatchDummy;

public class MatchVerifiedEventDummy {

    public static MatchVerifiedEvent matchVerifiedEvent() {
        var match = MatchDummy.match();
        return new MatchVerifiedEvent(new Object(), match);
    }
}
