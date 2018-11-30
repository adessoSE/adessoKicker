package de.adesso.kicker.match;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import de.adesso.kicker.team.Team;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

@Entity
@Table(name = "match")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long matchId;

    @NotNull(message = "Bitte ein Datum wählen.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date date;

    @NotNull(message = "Bitte eine Uhrzeit wählen.")
    @DateTimeFormat(pattern = "HH:mm")
    @Temporal(TemporalType.TIME)
    @Nullable
    private Date time;

    @OneToOne(targetEntity = Team.class)
    private Team winner;

    private String kicker;

    @ManyToOne(targetEntity = Team.class)
    private Team teamA;

    @ManyToOne(targetEntity = Team.class)
    private Team teamB;

    public Match() {
    }

    public String getGermanDate() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return matchId == match.matchId &&
                Objects.equals(date, match.date) &&
                Objects.equals(time, match.time) &&
                Objects.equals(winner, match.winner) &&
                Objects.equals(kicker, match.kicker) &&
                Objects.equals(teamA, match.teamA) &&
                Objects.equals(teamB, match.teamB);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matchId, date, time, winner, kicker, teamA, teamB);
    }
}
