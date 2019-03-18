package de.adesso.kicker.user.persistence;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String statisticId;

    @NotNull
    private int rating;

    private Integer rank;

    private Long wins;

    private Long losses;

    public Statistics() {
        this.rating = 1000;
        this.wins = 0L;
        this.losses = 0L;
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
