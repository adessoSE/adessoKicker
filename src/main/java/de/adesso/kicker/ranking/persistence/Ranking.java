package de.adesso.kicker.ranking.persistence;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Ranking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String rankingId;

    @NotNull
    private int rating;

    public Ranking() {
        this.rating = 1000;
    }

    public void updateRating(double rating) {
        this.rating += rating;
    }
}
