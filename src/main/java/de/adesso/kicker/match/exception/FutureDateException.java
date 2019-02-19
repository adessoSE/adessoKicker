package de.adesso.kicker.match.exception;

public class FutureDateException extends RuntimeException {
    public FutureDateException() {
        super("A match can't have been played in the future");
    }
}
