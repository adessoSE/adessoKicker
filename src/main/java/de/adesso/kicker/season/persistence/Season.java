package de.adesso.kicker.season.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Season {

    @Id
    private String season;

    @OneToMany(cascade = CascadeType.ALL)
    private List<SeasonMatch> seasonMatches;

    @OneToMany(cascade = CascadeType.ALL)
    private List<SeasonTrackedStatistic> seasonTrackedStatistics;
}
