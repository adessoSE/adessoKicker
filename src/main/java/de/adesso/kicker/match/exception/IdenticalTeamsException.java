package de.adesso.kicker.match.exception;

public class IdenticalTeamsException extends RuntimeException {
    IdenticalTeamsException() {
        super("Identical teams selected.");
    }
}
