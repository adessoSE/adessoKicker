package de.adesso.kicker.match.service.events;

import de.adesso.kicker.match.persistence.Match;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class MatchCreatedEvent extends ApplicationEvent {

    private final Match match;

    public MatchCreatedEvent(Object source, Match match) {
        super(source);
        this.match = match;
    }
}
