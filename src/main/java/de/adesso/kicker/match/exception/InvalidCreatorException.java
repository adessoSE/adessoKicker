package de.adesso.kicker.match.exception;

public class InvalidCreatorException extends RuntimeException {
    public InvalidCreatorException() {
        super("Current user is not the same as Player1 in Team A");
    }
}
