package de.adesso.adessoKicker.repositories;

import de.adesso.adessoKicker.objects.Match;
import org.springframework.data.repository.CrudRepository;

public interface MatchRepository extends CrudRepository<Match, Long> {

    Match findByMatchId(Long id);
}
