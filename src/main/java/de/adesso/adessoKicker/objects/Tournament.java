package de.adesso.adessoKicker.objects;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



@Entity
@Table(name="tournament")
public class Tournament {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private long tournamentId;

	private String tournamentName;
	private Date startDate;
	private Date endDate;
	private String format;

	@OneToMany(targetEntity= Match.class)
	private List<Match> matches;
	//Ein Tournament hat viele Teams
    @OneToMany(targetEntity = Team.class)
    private List<Team> teams;

	protected Tournament() {

	}

	public Tournament(String tournamentName, Date startDate, Date endDate, String format, List<Team> teams) {
		this.tournamentName = tournamentName;
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

	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
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
                '}';
    }
}