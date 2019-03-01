package de.adesso.kicker.match.persistence;

import de.adesso.kicker.user.persistence.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@Table(name = "match")
@Entity
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String matchId;

    @NotNull
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

    @NotNull
    private Boolean winnerTeamA;

    private boolean verified;

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
        players.removeIf(Objects::isNull);
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
        winners.removeIf(Objects::isNull);
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
        losers.removeIf(Objects::isNull);
        return losers;
    }
}
