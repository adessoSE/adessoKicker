package de.adesso.kicker.tournament.singleelimination;

public class TeamAlreadyInTournamentException extends RuntimeException {

    private static final long serialVersionUID = -684307995310482175L;

    public TeamAlreadyInTournamentException() {
        super("This team is already in the tournament");
    }

}
