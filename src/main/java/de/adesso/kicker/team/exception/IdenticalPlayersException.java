package de.adesso.kicker.team.exception;

public class IdenticalPlayersException extends RuntimeException {

    public IdenticalPlayersException() {
        super("You cannot play with identical players.");
    }

}
