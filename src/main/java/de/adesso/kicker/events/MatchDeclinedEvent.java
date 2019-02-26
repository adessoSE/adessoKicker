package de.adesso.kicker.events;

import de.adesso.kicker.match.Match;
import org.springframework.context.ApplicationEvent;

public class MatchDeclinedEvent extends ApplicationEvent {

    private Match match;

    public MatchDeclinedEvent(Object source, Match match) {
        super(source);
        this.match = match;
    }

    public Match getMatch() {
        return this.match;
    }
}
