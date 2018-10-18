package de.adesso.kicker.match;

import org.springframework.data.repository.CrudRepository;

public interface MatchRepository extends CrudRepository<Match, Long> {

    /**
     * findByMatchId() finds a match by it's id.
     * 
     * @param id
     * @return
     */
    Match findByMatchId(Long id);
}
