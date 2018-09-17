package de.adesso.adessoKicker.objects;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long matchId;

    private Date date;

    private int teamAPoints;

    private int teamBPoints;

    private Team winner;

    private String kicker;

    private Tournament tournament;

    private Team teamA;

    private Team teamB;

    public Match() {
        Team teamA = new Team();
        Team teamB = new Team();
        Team winner = new Team();
    }

    public Match(long matchId, Date date, int teamAPoints, int teamBPoints, Team winner, String kicker, Tournament tournament, Team teamA, Team teamB) {

        this.matchId = matchId;
        this.date = date;
        this.teamAPoints = teamAPoints;
        this.teamBPoints = teamBPoints;
        this.winner = winner;
        this.kicker = kicker;
        this.tournament = tournament;
        this.teamA = teamA;
        this.teamB = teamB;

    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTeamAPoints() {
        return teamAPoints;
    }

    public void setTeamAPoints(int teamAPoints) {
        this.teamAPoints = teamAPoints;
    }

    public int getTeamBPoints() {
        return teamBPoints;
    }

    public void setTeamBPoints(int teamBPoints) {
        this.teamBPoints = teamBPoints;
    }

    public Team getWinner() {
        return winner;
    }

    public void setWinner(Team winner) {
        this.winner = winner;
    }

    public String getKicker() {
        return kicker;
    }

    public void setKicker(String kicker) {
        this.kicker = kicker;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Team getTeamA() {
        return teamA;
    }

    public void setTeamA(Team teamA) {
        this.teamA = teamA;
    }

    public Team getTeamB() {
        return teamB;
    }

    public void setTeamB(Team teamB) {
        this.teamB = teamB;
    }
}
