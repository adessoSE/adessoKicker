package de.adesso.kicker.tournament.singleelimination;

public class TeamAlreadyInTournamentException extends RuntimeException {

    public TeamAlreadyInTournamentException() {
        super("This team is already in the tournament");
    }

}
