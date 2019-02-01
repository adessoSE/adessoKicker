package de.adesso.kicker.match;

import de.adesso.kicker.user.User;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "match")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long matchId;

    @NotNull(message = "Bitte ein Datum wählen.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @NotNull
    @OneToOne
    private User teamAPlayer1;

    @Nullable
    @OneToOne
    private User teamAPlayer2;

    @NotNull
    @OneToOne
    private User teamBPlayer1;

    @Nullable
    @OneToOne
    private User teamBPlayer2;

    private Boolean winnerIsTeamA;

    private boolean verified;

    public Match() {
    }

    public Match(LocalDate date, User teamAPlayer1, User teamAPlayer2, User teamBPlayer1, User teamBPlayer2) {
        this.date = date;
        this.teamAPlayer1 = teamAPlayer1;
        this.teamAPlayer2 = teamAPlayer2;
        this.teamBPlayer1 = teamBPlayer1;
        this.teamBPlayer2 = teamBPlayer2;
        winnerIsTeamA = null;
        verified = false;
    }

    public Match(LocalDate date, User teamAPlayer1, User teamBPlayer1) {
        this.date = date;
        this.teamAPlayer1 = teamAPlayer1;
        this.teamAPlayer2 = null;
        this.teamBPlayer1 = teamBPlayer1;
        this.teamBPlayer2 = null;
        winnerIsTeamA = null;
        verified = false;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getTeamAPlayer1() {
        return teamAPlayer1;
    }

    public void setTeamAPlayer1(User teamAPlayer1) {
        this.teamAPlayer1 = teamAPlayer1;
    }

    @Nullable
    public User getTeamAPlayer2() {
        return teamAPlayer2;
    }

    public void setTeamAPlayer2(@Nullable User teamAPlayer2) {
        this.teamAPlayer2 = teamAPlayer2;
    }

    public User getTeamBPlayer1() {
        return teamBPlayer1;
    }

    public void setTeamBPlayer1(User teamBPlayer1) {
        this.teamBPlayer1 = teamBPlayer1;
    }

    @Nullable
    public User getTeamBPlayer2() {
        return teamBPlayer2;
    }

    public void setTeamBPlayer2(@Nullable User teamBPlayer2) {
        this.teamBPlayer2 = teamBPlayer2;
    }

    public Boolean getWinnerIsTeamA() {
        return winnerIsTeamA;
    }

    public void setWinnerIsTeamA(Boolean winnerIsTeamA) {
        this.winnerIsTeamA = winnerIsTeamA;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Match match = (Match) o;
        return matchId == match.matchId && verified == match.verified && date.equals(match.date)
                && teamAPlayer1.equals(match.teamAPlayer1) && Objects.equals(teamAPlayer2, match.teamAPlayer2)
                && teamBPlayer1.equals(match.teamBPlayer1) && Objects.equals(teamBPlayer2, match.teamBPlayer2)
                && Objects.equals(winnerIsTeamA, match.winnerIsTeamA);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matchId, date, teamAPlayer1, teamAPlayer2, teamBPlayer1, teamBPlayer2, winnerIsTeamA,
                verified);
    }

    @Override
    public String toString() {
        return "Match{" + "matchId=" + matchId + ", date=" + date + ", teamAPlayer1=" + teamAPlayer1 + ", teamAPlayer2="
                + teamAPlayer2 + ", teamBPlayer1=" + teamBPlayer1 + ", teamBPlayer2=" + teamBPlayer2
                + ", winnerIsTeamA=" + winnerIsTeamA + ", verified=" + verified + '}';
    }
}
