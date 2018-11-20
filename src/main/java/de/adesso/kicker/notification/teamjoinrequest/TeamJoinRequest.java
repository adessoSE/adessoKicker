package de.adesso.kicker.notification.teamjoinrequest;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.adesso.kicker.notification.Notification;
import de.adesso.kicker.team.Team;
import de.adesso.kicker.user.User;

@Entity
public class TeamJoinRequest extends Notification {

    private String teamName;

    public TeamJoinRequest() {

        super();
    }

    public TeamJoinRequest(String teamName, User sender, User receiver) {
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
