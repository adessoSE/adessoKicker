package de.adesso.kicker.notification.teamjoinrequest;

import java.util.Date;

import javax.persistence.Entity;

import de.adesso.kicker.notification.Notification;
import de.adesso.kicker.user.User;

@Entity
public class TeamJoinRequest extends Notification {

    private String teamName;

    public TeamJoinRequest() {

        type = NotificationType.TeamJoinRequest;
    }

    public TeamJoinRequest(User sender, User receiver, String teamName) {
        this();
        this.teamName = teamName;
        this.sender = sender;
        this.receiver = receiver;
        this.sendDate = new Date();
        this.message = generateMessage();
    }

    public String generateMessage() {
        return sender.getFirstName() + " " + sender.getLastName() + " has invited you to join team: " + teamName;
    }

    public String getTeamName() {
        return this.teamName;
    }
}
