package de.adesso.kicker.ranking.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class Ranking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String rankingId;

    @NotNull
    private int rating;

    public Ranking() {
        this.rating = 1500;
    }

    public String getRankingId() {
        return rankingId;
    }

    public void setRankingId(String rankingId) {
        this.rankingId = rankingId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Ranking{" + "rankingId='" + rankingId + '\'' + ", rating=" + rating + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Ranking ranking = (Ranking) o;
        return rating == ranking.rating && rankingId.equals(ranking.rankingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rankingId, rating);
    }
}
