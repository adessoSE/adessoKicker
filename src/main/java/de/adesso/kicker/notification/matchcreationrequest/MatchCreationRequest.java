package de.adesso.kicker.notification.matchcreationrequest;

import de.adesso.kicker.notification.Notification;
import de.adesso.kicker.notification.NotificationType;
import de.adesso.kicker.team.Team;
import de.adesso.kicker.user.User;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class MatchCreationRequest extends Notification {

    @OneToOne(targetEntity = Team.class)
    private Team teamA;
    @OneToOne(targetEntity = Team.class)
    private Team teamB;
    @Temporal(TemporalType.DATE)
    private Date date;
    @Temporal(TemporalType.TIME)
    private Date time;
    private String kicker;
    @ManyToOne(targetEntity = MatchCreationValidation.class)
    MatchCreationValidation matchCreationValidation;

    public MatchCreationRequest(User sender, User receiver, Team teamA, Team teamB, Date date, Date time, String kicker,
            MatchCreationValidation matchCreationValidation) {
        super(sender, receiver);
        this.teamA = teamA;
        this.teamB = teamB;
        this.date = date;
        this.time = time;
        this.kicker = kicker;
        this.matchCreationValidation = matchCreationValidation;
        setType(NotificationType.MATCH_CREATION_REQUEST);
    }

    @Override
    public String generateMessage() {
        return getSender().getFirstName() + " " + getSender().getLastName() + " asked you to play a match: "
                + getGermanDate() + " " + time + " " + teamA.getTeamName() + " vs. " + teamB.getTeamName() + " at "
                + kicker;
    }

    public String getGermanDate() {
        DateFormat df = new SimpleDateFormat("EEEEE, dd. MMMMM yyyy");
        return df.format(date);
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

    @Override
    public String toString() {
        return "MatchCreationRequest{" + "teamA=" + teamA + ", teamB=" + teamB + ", date=" + date + ", time=" + time
                + ", kicker='" + kicker + '\'' + ", matchCreationValidation=" + matchCreationValidation + "} "
                + super.toString();
    }
}
