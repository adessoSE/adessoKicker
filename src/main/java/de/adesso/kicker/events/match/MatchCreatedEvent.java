package de.adesso.kicker.events.match;

import de.adesso.kicker.match.persistence.Match;
import org.springframework.context.ApplicationEvent;

public class MatchCreatedEvent extends ApplicationEvent {

    private Match match;

    public MatchCreatedEvent(Object source, Match match) {
        super(source);
        this.match = match;
    }

    public Match getMatch() {
        return match;
    }
}
