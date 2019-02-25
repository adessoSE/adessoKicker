package de.adesso.kicker.match.exception;

public class SamePlayerException extends RuntimeException {
    public SamePlayerException() {
        super("The same player can't play in the same match twice");
    }
}
