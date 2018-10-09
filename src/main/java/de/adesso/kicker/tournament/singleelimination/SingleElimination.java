package de.adesso.kicker.tournament.singleelimination;

import de.adesso.kicker.team.Team;
import de.adesso.kicker.tournament.Tournament;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.IndexColumn;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class SingleElimination extends Tournament {

    // @ManyToMany(targetEntity = Team.class, cascade = CascadeType.ALL)
    @Column
    @ElementCollection
    private List<ArrayList<Team>> bracket;

    @ManyToMany(targetEntity = Team.class, cascade = CascadeType.ALL)
    private List<Team> teams;

    private Team winner;

    public SingleElimination() {

        this.setFormat("SINGLEELIMINATION");
    }

    public SingleElimination(String tournamentName) {

        super(tournamentName);
        this.bracket = new ArrayList<ArrayList<Team>>();
        this.teams = new ArrayList<>();
        this.winner = null;
    }

    public void addTeam(Team team) {
        teams.add(team);
    }

    public void removeTeam(Team team) {
        teams.remove(team);
    }

    public List<ArrayList<Team>> getBracket() {
        return bracket;
    }

    public void setBracket(List<ArrayList<Team>> bracket) {
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
