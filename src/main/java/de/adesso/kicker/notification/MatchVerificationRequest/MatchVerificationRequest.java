package de.adesso.kicker.notification.MatchVerificationRequest;

import de.adesso.kicker.match.Match;
import de.adesso.kicker.notification.Notification;
import de.adesso.kicker.notification.NotificationType;
import de.adesso.kicker.user.User;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.text.MessageFormat;
import java.util.Properties;

@Entity
public class MatchVerificationRequest extends Notification {

    @ManyToOne(targetEntity = Match.class, cascade = CascadeType.ALL)
    private Match match;

    public MatchVerificationRequest(User sender, User receiver, Match match) {

        super(sender, receiver);
        super.setType(NotificationType.MATCH_VERIFICATION);
        this.match = match;
        generateMessage();
    }

    public Match getMatch() {

        return match;
    }

    public void generateMessage() {

        Properties properties = new Properties();
        String message;
        if (getMatch().getWinners().size() > 1) {
            message = MessageFormat.format(properties.getProperty("notification.matchrequest.twoopponents"),
                    getMatch().getWinners().get(0).getFirstName(), getMatch().getWinners().get(1).getFirstName(),
                    getMatch().getDate().toString());
        } else {
            message = MessageFormat.format(properties.getProperty("notification.matchrequest.oneopponent"),
                    getMatch().getWinners().get(0).getFirstName(), getMatch().getDate().toString());
        }
        setMessage(message);
    }
}
