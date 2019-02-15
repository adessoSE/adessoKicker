package de.adesso.kicker.notification.MatchVerificationRequest;

import de.adesso.kicker.match.Match;
import de.adesso.kicker.notification.Notification;
import de.adesso.kicker.notification.NotificationType;
import de.adesso.kicker.user.User;
import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;

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

    public void generateMessage() {

        String message = "Bestätige, dass " + getMatch().getWinners().get(0).getFirstName();
        if(getMatch().getWinners().toArray().length > 1){
            message += " und " + getMatch().getWinners().get(1).getFirstName() + " das Match am " + getMatch().getDate().toString() + " gewonnen haben.";
        } else {
            message += " das Match am " + getMatch().getDate().toString() + " gewonnen hat.";
        }
        setMessage(message);
    }
}
