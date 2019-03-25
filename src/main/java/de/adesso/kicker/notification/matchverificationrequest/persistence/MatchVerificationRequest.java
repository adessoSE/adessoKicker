package de.adesso.kicker.notification.matchverificationrequest.persistence;

import de.adesso.kicker.match.persistence.Match;
import de.adesso.kicker.notification.persistence.Notification;
import de.adesso.kicker.notification.persistence.NotificationType;
import de.adesso.kicker.user.persistence.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@NoArgsConstructor
@Entity
public class MatchVerificationRequest extends Notification {

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    private Match match;

    public MatchVerificationRequest(User sender, User receiver, Match match) {
        super(sender, receiver);
        super.setType(NotificationType.MATCH_VERIFICATION);
        this.match = match;
    }
}
