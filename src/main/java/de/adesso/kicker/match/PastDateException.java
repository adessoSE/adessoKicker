package de.adesso.kicker.match;

public class PastDateException extends RuntimeException {
    PastDateException() {
        super("Date has already passed through.");
    }
}
