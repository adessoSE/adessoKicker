package de.adesso.kicker.match.exception;

public class NoDateException extends RuntimeException {
    public NoDateException() {
        super("No date has been entered");
    }
}
