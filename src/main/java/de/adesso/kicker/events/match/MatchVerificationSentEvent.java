package de.adesso.kicker.events.match;

import de.adesso.kicker.notification.matchverificationrequest.persistence.MatchVerificationRequest;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class MatchVerificationSentEvent extends ApplicationEvent {

    private MatchVerificationRequest matchVerificationRequest;

    public MatchVerificationSentEvent(Object source, MatchVerificationRequest matchVerificationRequest) {
        super(source);
        this.matchVerificationRequest = matchVerificationRequest;
    }
}
