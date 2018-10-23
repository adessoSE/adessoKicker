package de.adesso.kicker.team;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface TeamRepository extends CrudRepository<Team, Long> {

    /**
     * findByTeamName() finds a team by it's name.
     * 
     * @param teamName
     * @return
     */
    Team findByTeamName(String teamName);

    /**
     * findByTeamId() finds a team by it's id.
     * 
     * @param id
     * @return
     */
    Team findByTeamId(Long id);

    /**
     * findByTeamNameContainingIgnoreCase() finds teams by the same teamName
     * ignoring the case or something similar to it.
     * 
     * @param teamName
     * @return
     */
    List<Team> findByTeamNameContainingIgnoreCase(String teamName);
}
