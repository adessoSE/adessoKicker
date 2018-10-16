package de.adesso.kicker.match;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.*;

import de.adesso.kicker.team.Team;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "match")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long matchId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date date;

    @DateTimeFormat(pattern = "HH:mm")
    @Temporal(TemporalType.TIME)
    private Date time;

    @OneToOne(targetEntity = Team.class)
    private Team winner;

    private String kicker;

    @ManyToOne(targetEntity = Team.class)
    private Team teamA;

    @ManyToOne(targetEntity = Team.class)
    private Team teamB;

    public Match() {
        date = new Date();
        time = new Date();

    }

    public String getGermanDate(){
        DateFormat df = new SimpleDateFormat("EEEEE, dd. MMMMM yyyy");
        String germanDate = df.format(date);
        return germanDate;
    }

    public Match(Date date, Date time, String kicker, Team teamA, Team teamB) {

        this.date = date;
        this.time = time;
        this.winner = null;
        this.kicker = kicker;
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
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

    @Override
    public String toString() {
        return "Match{" + "matchId=" + matchId + ", date=" + date + ", winner=" + winner + ", kicker='" + kicker + '\''
                + ", teamA=" + teamA + ", teamB=" + teamB + '}';
    }
}
