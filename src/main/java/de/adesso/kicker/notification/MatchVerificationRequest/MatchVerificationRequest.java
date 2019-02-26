package de.adesso.kicker.notification.MatchVerificationRequest;

import de.adesso.kicker.match.Match;
import de.adesso.kicker.notification.Notification;
import de.adesso.kicker.notification.NotificationType;
import de.adesso.kicker.user.User;
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
