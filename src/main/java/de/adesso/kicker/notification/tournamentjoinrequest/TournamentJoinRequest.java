package de.adesso.kicker.notification.tournamentjoinrequest;

import de.adesso.kicker.notification.Notification;
import de.adesso.kicker.team.Team;
import de.adesso.kicker.tournament.Tournament;
import de.adesso.kicker.user.User;
import java.util.Date;

public class TournamentJoinRequest extends Notification {

    private Tournament targetTournament;
    private Team targetTeam;

    public TournamentJoinRequest() {

        type = NotificationType.TournamentJoinRequest;
    }

    public TournamentJoinRequest(Tournament tournament, User sender, User receiver, Team team) {
        this();
        this.targetTournament = tournament;
        this.targetTeam = team;
        this.sender = sender;
        this.receiver = receiver;
        this.sendDate = new Date();
        this.message = generateMessage();
    }

    public String generateMessage() {
        return sender.getFirstName() + " " + sender.getLastName() + " asked you to join tournament: "
                + targetTournament.getTournamentName() + " with your team: " + targetTeam.getTeamName();
    }

    public Team getTargetTeam() {
        return targetTeam;
    }

    public Tournament getTargetTournament () { return targetTournament; }
}
