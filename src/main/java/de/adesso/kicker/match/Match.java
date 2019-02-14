package de.adesso.kicker.match;

import de.adesso.kicker.user.User;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "match")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String matchId;

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

    private Boolean winnerTeamA;

    private boolean verified;

    public Match() {
    }

    public Match(LocalDate date, User teamAPlayer1, User teamAPlayer2, User teamBPlayer1, User teamBPlayer2,
            Boolean winnerTeamA) {
        this.date = date;
        this.teamAPlayer1 = teamAPlayer1;
        this.teamAPlayer2 = teamAPlayer2;
        this.teamBPlayer1 = teamBPlayer1;
        this.teamBPlayer2 = teamBPlayer2;
        this.winnerTeamA = winnerTeamA;
        this.verified = false;
    }

    public Match(LocalDate date, User teamAPlayer1, User teamBPlayer1, Boolean winnerTeamA) {
        this.date = date;
        this.teamAPlayer1 = teamAPlayer1;
        this.teamAPlayer2 = null;
        this.teamBPlayer1 = teamBPlayer1;
        this.teamBPlayer2 = null;
        this.winnerTeamA = winnerTeamA;
        this.verified = false;
    }

    public List<User> getPlayers() {
        var players = new ArrayList<User>();
        players.add(teamAPlayer1);
        players.add(teamAPlayer2);
        players.add(teamBPlayer1);
        players.add(teamBPlayer2);
        return players;
    }

    public List<User> getWinners() {
        var winners = new ArrayList<User>();
        if (winnerTeamA) {
            winners.add(teamAPlayer1);
            winners.add(teamAPlayer2);
        } else {
            winners.add(teamBPlayer1);
            winners.add(teamBPlayer2);
        }
        return winners;
    }

    public List<User> getLosers() {
        var losers = new ArrayList<User>();
        if (!winnerTeamA) {
            losers.add(teamAPlayer1);
            losers.add(teamAPlayer2);
        } else {
            losers.add(teamBPlayer1);
            losers.add(teamBPlayer2);
        }
        return losers;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
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

    public Boolean getWinnerTeamA() {
        return winnerTeamA;
    }

    public void setWinnerTeamA(Boolean winnerTeamA) {
        this.winnerTeamA = winnerTeamA;
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
        return verified == match.verified && Objects.equals(matchId, match.matchId) && Objects.equals(date, match.date)
                && Objects.equals(teamAPlayer1, match.teamAPlayer1) && Objects.equals(teamAPlayer2, match.teamAPlayer2)
                && Objects.equals(teamBPlayer1, match.teamBPlayer1) && Objects.equals(teamBPlayer2, match.teamBPlayer2)
                && Objects.equals(winnerTeamA, match.winnerTeamA);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matchId, date, teamAPlayer1, teamAPlayer2, teamBPlayer1, teamBPlayer2, winnerTeamA,
                verified);
    }

    @Override
    public String toString() {
        return "Match{" + "matchId=" + matchId + ", date=" + date + ", teamAPlayer1=" + teamAPlayer1 + ", teamAPlayer2="
                + teamAPlayer2 + ", teamBPlayer1=" + teamBPlayer1 + ", teamBPlayer2=" + teamBPlayer2 + ", winnerTeamA="
                + winnerTeamA + ", verified=" + verified + '}';
    }
}
