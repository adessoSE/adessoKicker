package de.adesso.kicker.season.service;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
class SeasonEndedEvent extends ApplicationEvent {

    private final String seasonName;

    SeasonEndedEvent(Object source, String seasonName) {
        super(source);
        this.seasonName = seasonName;
    }
}
