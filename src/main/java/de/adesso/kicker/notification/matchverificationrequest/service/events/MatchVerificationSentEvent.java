package de.adesso.kicker.notification.matchverificationrequest.service.events;

import de.adesso.kicker.notification.matchverificationrequest.persistence.MatchVerificationRequest;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class MatchVerificationSentEvent extends ApplicationEvent {

    private final MatchVerificationRequest matchVerificationRequest;

    public MatchVerificationSentEvent(Object source, MatchVerificationRequest matchVerificationRequest) {
        super(source);
        this.matchVerificationRequest = matchVerificationRequest;
    }
}
