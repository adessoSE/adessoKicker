package de.adesso.adessoKicker.objects;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Entity
@Table(name="tbTeam")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long matchId;
	private Date date;
	private int teamAPoints;
	private int teamBPoints;
	private Team teamA;
	private Team teamB;
	private Team winner;
	private String kicker;
	private Tournament tournament;

	
	public Team() {
		teamA = new Team();
		teamB = new Team();
		tournament = new Tournament();
	}
	
	public Team(long matchId, Date date, int teamAPoints, int teamBPoints, Team winner, String kicker,
			Tournament tournament) {
		super();
		this.matchId = matchId;
		this.date = date;
		this.teamAPoints = teamAPoints;
		this.teamBPoints = teamBPoints;
		this.winner = winner;
		this.kicker = kicker;
		this.tournament = tournament;
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
	
}
