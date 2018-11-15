package de.adesso.kicker.tournament.singleelimination;

public class PlayerInTournamentException extends RuntimeException {

    public PlayerInTournamentException() {
        super("A player of this team is already in this tournament");
    }

}
