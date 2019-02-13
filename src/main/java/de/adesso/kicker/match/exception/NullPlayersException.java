package de.adesso.kicker.match.exception;

public class NullPlayersException extends RuntimeException {
    public NullPlayersException() {
        super("Player 1 of Team A or B is Null");
    }
}
