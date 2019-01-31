package de.adesso.kicker.team.exception;

public class TeamNameExistingException extends RuntimeException {

    public TeamNameExistingException() {
        super("Teamname already exists.");
    }

}
