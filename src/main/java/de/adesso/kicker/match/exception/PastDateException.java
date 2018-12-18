package de.adesso.kicker.match.exception;

public class PastDateException extends RuntimeException {
    PastDateException() {
        super("Date has already passed through.");
    }
}
