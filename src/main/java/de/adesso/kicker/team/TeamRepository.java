package de.adesso.kicker.team;

import org.springframework.data.repository.CrudRepository;

public interface TeamRepository extends CrudRepository<Team, Long> {

    /** Finds Team with String teamName */
    Team findByTeamName(String teamName);

    Team findByTeamId(Long id);
}
