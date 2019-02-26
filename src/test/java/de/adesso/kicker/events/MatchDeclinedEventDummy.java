package de.adesso.kicker.events;

import de.adesso.kicker.match.MatchDummy;

public class MatchDeclinedEventDummy {

    public static MatchDeclinedEvent matchDeclinedEvent() {
        var match = MatchDummy.match();
        var matchDeclinedEvent = new MatchDeclinedEvent(new Object(), match);
        return matchDeclinedEvent;
    }
}
