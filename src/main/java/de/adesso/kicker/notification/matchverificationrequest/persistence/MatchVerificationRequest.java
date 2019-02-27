package de.adesso.kicker.notification.matchverificationrequest.persistence;

import de.adesso.kicker.match.persistence.Match;
import de.adesso.kicker.notification.persistence.Notification;
import de.adesso.kicker.notification.persistence.NotificationType;
import de.adesso.kicker.user.persistence.User;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class MatchVerificationRequest extends Notification {

    @ManyToOne(targetEntity = Match.class, cascade = CascadeType.ALL)
    private Match match;

    public MatchVerificationRequest(User sender, User receiver, Match match) {
        super(sender, receiver);
        super.setType(NotificationType.MATCH_VERIFICATION);
        this.match = match;
    }

    public Match getMatch() {
        return match;
    }
}
