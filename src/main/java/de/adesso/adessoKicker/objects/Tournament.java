package de.adesso.adessoKicker.objects;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tbTournament")
public class Tournament {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long tournamentId;
	private Date startDate;
	private Date endDate;
	String format;
	@ManyToOne
	List<Team> teams;
	
	public Tournament() {
		teams = new ArrayList<Team>();
		
	}
	
	public Tournament(long tournamentId, Date startDate, Date endDate, String format, List<Team> teams) {
		super();
		this.tournamentId = tournamentId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.format = format;
		this.teams = teams;
	}
	public long getTournamentId() {
		return tournamentId;
	}
	public void setTournamentId(long tournamentId) {
		this.tournamentId = tournamentId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public List<Team> getTeams() {
		return teams;
	}
	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}
	
	
}
