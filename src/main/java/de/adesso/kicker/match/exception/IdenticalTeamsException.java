package de.adesso.kicker.match.exception;

public class IdenticalTeamsException extends RuntimeException {
    public IdenticalTeamsException() {
        super("Identical teams selected.");
    }
}
