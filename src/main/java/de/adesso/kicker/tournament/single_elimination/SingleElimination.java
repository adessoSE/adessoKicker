package de.adesso.kicker.tournament.single_elimination;

import de.adesso.kicker.team.Team;
import de.adesso.kicker.tournament.Tournament;

import javax.persistence.Entity;
import java.util.List;

@Entity
public class SingleElimination extends Tournament {

    private List<List<Team>> bracket;
    private List<Team> teams;
    private Team winner;

    public List<List<Team>> getBracket() {
        return bracket;
    }

    public void setBracket(List<List<Team>> bracket) {
        this.bracket = bracket;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public Team getWinner() {
        return winner;
    }

    public void setWinner(Team winner) {
        this.winner = winner;
    }
}
