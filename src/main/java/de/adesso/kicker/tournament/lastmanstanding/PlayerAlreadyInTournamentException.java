package de.adesso.kicker.tournament.lastmanstanding;

public class PlayerAlreadyInTournamentException extends RuntimeException{

    private static final long serialVersionUID = 2848819969898886059L;

    public PlayerAlreadyInTournamentException() {
        super("Player is already in tournament");
    }
}
