package de.adesso.kicker.match;

public class PasteDateException extends RuntimeException {
    PasteDateException() {
        super("Date has already passed through.");
    }
}
