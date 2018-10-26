package de.adesso.kicker.tournament.singleelimination;

public class PlayerOfTeamAlreadyInTournamentException extends RuntimeException {

    private static final long serialVersionUID = -5272721983305955464L;

    public PlayerOfTeamAlreadyInTournamentException() {
        super("A player of this team is already in this tournament");
    }

}
