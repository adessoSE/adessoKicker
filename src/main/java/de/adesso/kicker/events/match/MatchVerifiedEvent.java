package de.adesso.kicker.events.match;

import de.adesso.kicker.match.persistence.Match;
import org.springframework.context.ApplicationEvent;

public class MatchVerifiedEvent extends ApplicationEvent {

    private Match match;

    public MatchVerifiedEvent(Object source, Match match) {
        super(source);
        this.match = match;
    }

    public Match getMatch() {
        return match;
    }
}
