package de.adesso.kicker.events;

import de.adesso.kicker.match.persistence.MatchDummy;
import de.adesso.kicker.match.service.events.MatchVerifiedEvent;

public class MatchVerifiedEventDummy {

    public static MatchVerifiedEvent matchVerifiedEvent() {
        var match = MatchDummy.match();
        return new MatchVerifiedEvent(new Object(), match);
    }
}
