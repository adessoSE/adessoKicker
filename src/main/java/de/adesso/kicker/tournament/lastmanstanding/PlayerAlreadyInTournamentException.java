package de.adesso.kicker.tournament.lastmanstanding;

public class PlayerAlreadyInTournamentException extends RuntimeException{
    public PlayerAlreadyInTournamentException() {
        super("Player is already in tournament");
    }
}
