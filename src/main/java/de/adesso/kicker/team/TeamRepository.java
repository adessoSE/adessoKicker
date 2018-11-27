package de.adesso.kicker.team;

import de.adesso.kicker.user.User;
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
     * @param id Id
     * @return Team
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

    List<Team> findByPlayerAOrPlayerB(User playerA, User playerB);
}
