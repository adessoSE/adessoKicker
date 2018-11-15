package de.adesso.kicker.tournament.singleelimination;

public class PlayerInTournamentException extends RuntimeException {

    public PlayerInTournamentException() {
        super("Player is already in this tournament");
    }

}
