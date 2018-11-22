package de.adesso.kicker.tournament.singleelimination;

public class PlayerOfTeamInTournamentException extends RuntimeException {

    public PlayerOfTeamInTournamentException() {
        super("A player of this team is already in this tournament");
    }

}
