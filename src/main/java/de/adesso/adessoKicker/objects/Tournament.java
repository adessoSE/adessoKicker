package de.adesso.adessoKicker.objects;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="tournament")
public class Tournament {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private long tournamentId;

	private String tournamentName;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startDate;

	private Date endDate;
	private String format;
	private String description;

	@OneToOne(targetEntity = Team.class, cascade = CascadeType.ALL)
	private Team winner;
	private  boolean finished;

	@OneToMany(targetEntity= Match.class)
	private List<Match> matches;

    @ManyToMany(targetEntity = Team.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	//@JoinTable(name = "tournament_team", joinColumns = @JoinColumn(name = "tournament_id"), inverseJoinColumns = @JoinColumn(name = "team_id"))
    private List<Team> teams;

    @ManyToMany(targetEntity =  User.class)
    private List<User> players;

    @Column
    @ElementCollection
    private List<ArrayList<Team>> tournamentTree;

	public Tournament() {
	}

	public Tournament(String tournamentName, Date startDate, String format) {
		this.tournamentName = tournamentName;
	    this.startDate = startDate;
        this.format = format;
        this.teams = new ArrayList<>();
        this.matches = new ArrayList<>();
        this.players = new ArrayList<>();
		this.winner = null;
		this.finished = false;
		this.description = description;
		this.tournamentTree = new ArrayList<ArrayList<Team>>();
	}

	public void addTeam(Team team) {
		teams.add(team);
	}

	public void removeTeam(Team team) {
		teams.remove(team);
	}

	public long getTournamentId() {
		return tournamentId;
	}

	public void setTournamentId(long tournamentId) {
		this.tournamentId = tournamentId;
	}

	public String getTournamentName() {
		return tournamentName;
	}

	public void setTournamentName(String tournamentName) {
		this.tournamentName = tournamentName;
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

	public Team getWinner() {
		return winner;
	}

	public void setWinner(Team winner) {
		this.winner = winner;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public List<Match> getMatches() {
		return matches;
	}

	public void setMatches(List<Match> matches) {
		this.matches = matches;
	}

	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	public List<User> getPlayers() {
		return players;
	}

	public void setPlayers(List<User> players) {
		this.players = players;
	}

	public List<ArrayList<Team>> getTournamentTree() {
		return tournamentTree;
	}

	public void setTournamentTree(List<ArrayList<Team>> tournamentTree) {
		this.tournamentTree = tournamentTree;
	}

	@Override
    public String toString() {
        return "Tournament{" +
                "tournamentId=" + tournamentId +
                ", tournamentName='" + tournamentName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", format='" + format + '\'' +
                ", teams=" + teams +
                ", description=" + description +
                '}';
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}