package de.adesso.kicker.season.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    private int rank;
    private int rating;

    private long wins;
    private long losses;

    private LocalDate date;
}
