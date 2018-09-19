package de.adesso.adessoKicker.repositories;

import de.adesso.adessoKicker.objects.Tournament;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TournamentRepository extends CrudRepository<Tournament, Long> {

    Tournament findByTournamentName(String tournamentName);

    List<Tournament> findByFormat(String format);

    List<Tournament> findByFinished(boolean finished);

    Tournament findByTournamentId(Long id);
}
