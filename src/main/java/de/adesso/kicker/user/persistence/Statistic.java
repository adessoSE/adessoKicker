package de.adesso.kicker.user.persistence;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Statistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String statisticId;

    @NotNull
    private int rating;
    private int rank;

    private long wins;
    private long losses;

    public Statistic() {
        this.rating = 1000;
        this.wins = 0;
        this.losses = 0;
    }

    public int getWinRatio() {
        if (losses == 0) {
            return 100;
        }
        return Math.round(100 - (100f / getPlayedMatches() * losses));
    }

    public long getPlayedMatches() {
        return wins + losses;
    }

    public void increaseWins() {
        this.wins += 1;
    }

    public void increaseLosses() {
        this.losses += 1;
    }

    public void updateRating(double rating) {
        this.rating += rating;
    }
}
