package de.adesso.kicker.team;

public class TeamNameExistingException extends RuntimeException {

    public TeamNameExistingException() {
        super("Teamname already exists.");
    }

}
