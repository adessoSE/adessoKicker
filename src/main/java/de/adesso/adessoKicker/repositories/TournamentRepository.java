package de.adesso.adessoKicker.repositories;

import de.adesso.adessoKicker.objects.Tournament;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TournamentRepository extends CrudRepository<Tournament, Long> {

    Tournament findByTournamentName(String tournamentName);

    /*
    List<Tournament> findByFormat(String format);

    /**
     * Returns a List of Tournaments with finished
     */
    List<Tournament> findByFinished(boolean finished);

    /**
     * Returns a single Tournament found with id
     */
    Tournament findByTournamentId(Long id);
}
