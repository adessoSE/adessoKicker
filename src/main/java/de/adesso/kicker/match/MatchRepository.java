package de.adesso.kicker.match;

import org.springframework.data.repository.CrudRepository;

public interface MatchRepository extends CrudRepository<Match, Long> {

    Match findByMatchId(Long id);
}
