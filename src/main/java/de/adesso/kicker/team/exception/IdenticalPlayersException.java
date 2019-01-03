package de.adesso.kicker.team.exception;

public class IdenticalPlayersException extends RuntimeException {

    IdenticalPlayersException() {
        super("You cannot play with identical players.");
    }

}
