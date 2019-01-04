package de.adesso.kicker.notification.matchverificationrequest;

import de.adesso.kicker.match.Match;
import de.adesso.kicker.notification.Notification;
import de.adesso.kicker.team.Team;
import de.adesso.kicker.tournament.Tournament;
import de.adesso.kicker.user.User;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class MatchVerificationRequest extends Notification {

    @ManyToOne(targetEntity = Match.class)
    private Match match;
    @ManyToOne(targetEntity = Tournament.class)
    private Tournament tournament;
    @ManyToOne(targetEntity = Team.class)
    private Team winner;

    public MatchVerificationRequest(User sender, User receiver, Match match, Team winner, Tournament tournament){

        this.type = NotificationType.MatchVerificationRequest;
        this.sender = sender;
        this.receiver = receiver;
        this.match = match;
        this.winner = winner;
        this.tournament = tournament;
        this.message = generateMessage();
        this.sendDate = new Date();
    }

    public Team getWinner(){
        return this.winner;
    }

    public Match getMatch (){
        return this.match;
    }

    public Tournament getTournament(){
        return this.tournament;
    }

    public String generateMessage(){
        return "Please verify if team " + this.winner.getTeamName() + " has won the match on " + match.getKicker() + " at " + match.getGermanDate();
    }

    @Override
    public String toString(){
        return "MatchVerificationRequest{" +
                "match=" + this.match +
                ", winner=" + this.winner +
                ", tournament=" + this.tournament +
                "} " + super.toString();
    }
}
