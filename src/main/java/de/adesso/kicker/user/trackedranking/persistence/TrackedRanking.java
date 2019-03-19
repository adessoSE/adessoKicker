package de.adesso.kicker.user.trackedranking.persistence;

import de.adesso.kicker.user.persistence.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
public class TrackedRanking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String rankingId;

    private int rank;
    private int rating;

    private long wins;
    private long losses;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    private User user;

    private LocalDate date;

    public TrackedRanking(int rank, int rating, long wins, long losses, User user) {
        this.rank = rank;
        this.rating = rating;
        this.wins = wins;
        this.losses = losses;
        this.user = user;
        this.date = LocalDate.now();
    }
}
