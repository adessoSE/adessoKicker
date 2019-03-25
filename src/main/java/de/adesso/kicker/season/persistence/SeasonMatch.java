package de.adesso.kicker.season.persistence;

import de.adesso.kicker.user.persistence.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
@Entity
public class SeasonMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seasonMatchId;

    @OneToOne
    private User teamAPlayer1;
    @OneToOne
    private User teamAPlayer2;

    @OneToOne
    private User teamBPlayer1;
    @OneToOne
    private User teamBPlayer2;

    private boolean winnerTeamA;

    private LocalDate date;
}
