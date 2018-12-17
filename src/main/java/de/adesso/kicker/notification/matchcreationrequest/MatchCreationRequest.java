package de.adesso.kicker.notification.matchcreationrequest;

import de.adesso.kicker.notification.Notification;
import de.adesso.kicker.team.Team;
import de.adesso.kicker.user.User;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MatchCreationRequest extends Notification {

    private Team teamA;
    private Team teamB;
    @Temporal(TemporalType.DATE)
    private Date date;
    @Temporal(TemporalType.TIME)
    private Date time;
    private String kicker;

    @ManyToOne(targetEntity = MatchCreationValidation.class, cascade = CascadeType.ALL)
    MatchCreationValidation matchCreationValidation;

    public MatchCreationRequest() {

        type = NotificationType.MatchCreationRequest;
    }

    public MatchCreationRequest(User sender, User receiver, Team teamA, Team teamB, Date date, Date time, String kicker, MatchCreationValidation matchCreationValidation) {
        this();
        this.teamA = teamA;
        this.teamB = teamB;
        this.date = date;
        this.time = time;
        this.kicker = kicker;
        this.sender = sender;
        this.matchCreationValidation = matchCreationValidation;
        this.receiver = receiver;
        this.sendDate = new Date();
        this.message = generateMessage();
    }

    public String generateMessage() {
        return sender.getFirstName() + " " + sender.getLastName() + " asked you to play a match: "
                + getGermanDate() + " " + time + " " + teamA.getTeamName() + " vs. " + teamB.getTeamName() + " at " + kicker;
    }

    public String getGermanDate() {
        DateFormat df = new SimpleDateFormat("EEEEE, dd. MMMMM yyyy");
        String germanDate = df.format(date);
        return germanDate;
    }

    public Team getTeamA() {
        return teamA;
    }

    public Team getTeamB() {
        return teamB;
    }

    public Date getDate() {
        return date;
    }

    public Date getTime() {
        return time;
    }

    public String getKicker() {
        return kicker;
    }

    public MatchCreationValidation getMatchCreationValidation() {
        return matchCreationValidation;
    }

}
