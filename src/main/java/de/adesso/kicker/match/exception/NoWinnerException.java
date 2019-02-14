package de.adesso.kicker.match.exception;

public class NoWinnerException extends RuntimeException {
    public NoWinnerException() {
        super("No winner has been entered");
    }
}
