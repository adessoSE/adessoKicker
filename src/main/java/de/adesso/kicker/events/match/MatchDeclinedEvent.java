package de.adesso.kicker.events.match;

import de.adesso.kicker.match.persistence.Match;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class MatchDeclinedEvent extends ApplicationEvent {

    private Match match;

    public MatchDeclinedEvent(Object source, Match match) {
        super(source);
        this.match = match;
    }
}
