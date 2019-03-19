package de.adesso.kicker.season.persistence;

import de.adesso.kicker.user.persistence.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class SeasonStatistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seasonStatisticId;

    @ManyToOne
    private User user;

    private int rank;
    private int rating;

    private long wins;
    private long losses;

    @OneToMany(cascade = CascadeType.ALL)
    private List<SeasonTrackedStatistic> seasonTrackedStatistics;
}
