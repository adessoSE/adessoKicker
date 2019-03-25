package de.adesso.kicker.season.persistence;

import de.adesso.kicker.user.persistence.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class SeasonTrackedStatistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seasonStatisticId;

    @ManyToOne
    private User user;

    private int rank;
    private int rating;

    private long wins;
    private long losses;

    private LocalDate date;
}
