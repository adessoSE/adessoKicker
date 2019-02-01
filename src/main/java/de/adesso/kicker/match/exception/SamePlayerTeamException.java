package de.adesso.kicker.match.exception;

public class SamePlayerTeamException extends RuntimeException {
    public SamePlayerTeamException() {
        super("The same player can't play in a team twice");
    }
}
