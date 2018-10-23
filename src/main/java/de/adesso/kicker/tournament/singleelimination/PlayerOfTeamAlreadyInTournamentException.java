package de.adesso.kicker.tournament.singleelimination;

public class PlayerOfTeamAlreadyInTournamentException extends RuntimeException {

    public PlayerOfTeamAlreadyInTournamentException() {
        super("A player of this team is already in this tournament");
    }

}
