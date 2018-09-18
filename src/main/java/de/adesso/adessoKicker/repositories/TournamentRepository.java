package de.adesso.adessoKicker.repositories;

import de.adesso.adessoKicker.objects.Tournament;
import org.springframework.data.repository.CrudRepository;

public interface TournamentRepository extends CrudRepository<Tournament, Long> {
}
