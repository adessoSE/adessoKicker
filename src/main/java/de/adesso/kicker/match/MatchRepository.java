package de.adesso.kicker.match;

import org.springframework.data.repository.CrudRepository;

public interface MatchRepository extends CrudRepository<Match, String> {
    Match findByMatchId(String id);
}
