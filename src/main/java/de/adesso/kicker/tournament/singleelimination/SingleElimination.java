package de.adesso.kicker.tournament.singleelimination;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import de.adesso.kicker.team.Team;
import de.adesso.kicker.tournament.Tournament;

@Entity
public class SingleElimination extends Tournament {

    @ManyToMany(cascade = CascadeType.ALL, targetEntity = BracketRow.class)
    private List<BracketRow> bracket;

    @ManyToMany(targetEntity = Team.class, cascade = CascadeType.ALL)
    private List<Team> teams;

    @ManyToOne(targetEntity = Team.class)
    private Team winner;

    public SingleElimination() {

        this.setFormat("Single Elimination");
    }

    public SingleElimination(String tournamentName) {

        super(tournamentName);
        this.bracket = new ArrayList<>();
        this.teams = new ArrayList<>();
        this.winner = null;
    }

    public void addTeam(Team team) {
        teams.add(team);
    }

    public void removeTeam(Team team) {
        teams.remove(team);
    }

    public List<BracketRow> getBracket() {
        return bracket;
    }

    public void setBracket(List<BracketRow> bracket) {
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

    @Override
    public String toString() {
        return "SingleElimination{" + super.toString() + "bracket=" + bracket + ", teams=" + teams + ", winner="
                + winner + '}';
    }
}
