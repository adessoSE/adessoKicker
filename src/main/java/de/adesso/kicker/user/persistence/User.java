package de.adesso.kicker.user.persistence;

import de.adesso.kicker.ranking.persistence.Ranking;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
public class User {

    @Id
    private String userId;

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String email;

    private long wins;
    private long losses;

    @OneToOne(cascade = CascadeType.ALL)
    private Ranking ranking;

    public User(String userId, String firstName, String lastName, String email, Ranking ranking) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.wins = 0;
        this.losses = 0;
        this.ranking = ranking;
    }

    public int getWinRatio() {
        if (getPlayedMatches() == 0 || losses == 0) {
            return 100;
        }
        return Math.round(100 - (100f / getPlayedMatches() * losses));
    }

    public Long getPlayedMatches() {
        return wins + losses;
    }

    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }

    public void increaseWins() {
        this.wins += 1;
    }

    public void increaseLosses() {
        this.losses += 1;
    }
}
