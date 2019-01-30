package de.adesso.kicker.notification.teamjoinrequest;

import java.util.Date;

import javax.persistence.Entity;

import de.adesso.kicker.notification.Notification;
import de.adesso.kicker.notification.NotificationType;
import de.adesso.kicker.user.User;

@Entity
public class TeamJoinRequest extends Notification {

    private String teamName;

    public TeamJoinRequest() {

        setType(NotificationType.TeamJoinRequest);
    }

    public TeamJoinRequest(User sender, User receiver, String teamName) {
        this();
        this.teamName = teamName;
        setSender(sender);
        setReceiver(receiver);
        setSendDate(new Date());
        setMessage(generateMessage());
    }

    public String generateMessage() {
        return getSender().getFirstName() + " " + getSender().getLastName() + " has invited you to join team: "
                + teamName;
    }

    public String getTeamName() {
        return this.teamName;
    }

    @Override
    public String toString() {
        return "TeamJoinRequest{" + "teamName='" + teamName + '\'' + "} " + super.toString();
    }
}
