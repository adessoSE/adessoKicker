package de.adesso.adessoKicker.repositories;

import de.adesso.adessoKicker.objects.Team;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TeamRepository extends CrudRepository<Team, Long> {

    /**
     * Finds Team with String teamName
     */
    Team findByTeamName(String teamName);

    Team findByTeamId(Long id);
}
