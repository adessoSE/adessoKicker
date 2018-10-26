package de.adesso.kicker.match;

public class IdenticalTeamsException extends RuntimeException {
    IdenticalTeamsException() {
        super("Identical teams selected.");
    }
}
