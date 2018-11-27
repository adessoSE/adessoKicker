package de.adesso.kicker.tournament;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TournamentRepository extends CrudRepository<Tournament, Long> {

    Tournament findByTournamentName(String tournamentName);

    List<Tournament> findByFormat(String format);

    /**
     * Returns a List of Tournaments with finished
     */
    List<Tournament> findByFinished(boolean finished);

    /** Returns a single Tournament found with id */
    Tournament findByTournamentId(Long id);

    // Returns a list o Tournaments that are not finished tan ordered by start date
    // ascending
    List<Tournament> findByFinishedFalseOrderByStartDateAsc();
}
