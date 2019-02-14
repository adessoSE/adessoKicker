package de.adesso.kicker.match.exception;

public class MatchNotFoundException extends RuntimeException {
    public MatchNotFoundException() {
        super("Match doesn't exist");
    }
}
