package de.adesso.kicker.notification.teamjoinrequest;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.adesso.kicker.notification.Notification;
import de.adesso.kicker.team.Team;
import de.adesso.kicker.user.User;

@Entity
public class TeamJoinRequest extends Notification{
    
    @ManyToOne(targetEntity = Team.class)
    private Team targetTeam;
    
    public TeamJoinRequest() {
        
        super();
    }
    
    public TeamJoinRequest(Team targetTeam, User sender, User receiver) {
        this.targetTeam = targetTeam;
        this.sender = sender;
        this.receiver = receiver;
        this.sendDate = new Date();
        this.message = generateMessage();
    }

    public String generateMessage() {
        return sender.getFirstName() +  " " + sender.getLastName() + " has invited you to join team: " + targetTeam.getTeamName();
    }
    
    public Team getTargetTeam() {
        return targetTeam;
    }

    public void setTargetTeam(Team targetTeam) {
        this.targetTeam = targetTeam;
    }

}
