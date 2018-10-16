package de.adesso.kicker.team;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface TeamRepository extends CrudRepository<Team, Long> {

    /** Finds Team with String teamName */
    Team findByTeamName(String teamName);

    Team findByTeamId(Long id);
    List<Team> findByTeamNameContainingIgnoreCase(String teamName);
}
