package de.adesso.kicker.user.trackedstatistic.persistence;

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
public class TrackedStatistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String trackedStatisticsId;

    private Integer rank;

    private Integer rating;

    private Long wins;

    private Long losses;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    private User user;

    private LocalDate date;

    public TrackedStatistic(Integer rank, Integer rating, Long wins, Long losses, User user) {
        this.rank = rank;
        this.rating = rating;
        this.wins = wins;
        this.losses = losses;
        this.user = user;
        this.date = LocalDate.now();
    }
}
