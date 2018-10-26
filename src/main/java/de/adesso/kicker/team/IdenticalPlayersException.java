package de.adesso.kicker.team;

public class IdenticalPlayersException extends RuntimeException {

    IdenticalPlayersException() {
        super("You cannot play with identical players.");
    }

}
