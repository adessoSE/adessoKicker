package de.adesso.kicker.team;

import de.adesso.kicker.match.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Service that handles "TeamService" used in "TeamController".
 */
@Service
public class TeamService {

    private TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository, EntityManager entityManager) {
        this.teamRepository = teamRepository;
    }

    private List<Team> teams;

    /**
     * getAllTeams() returns a list of all teams.
     * 
     * @return
     */
    public List<Team> getAllTeams() {

        teams = new ArrayList<>();
        teamRepository.findAll().forEach(teams::add);
        return teams;
    }

    /**
     * getTeamById() returns an unique team identified by it's id.
     * 
     * @param id
     * @return
     */
    public Team getTeamById(long id) {

        return teamRepository.findByTeamId(id);
    }

    /**
     * saveTeam() saves a team object.
     * 
     * @param team
     */
    public Team saveTeam(Team team) {

        return teamRepository.save(team);
    }

    /**
     * deleteTeamById() deletes an unique team by it's id.
     * 
     * @param id
     */
    public void deleteTeamById(long id) {

        teamRepository.deleteById(id);
    }

    /**
     * addMatchIdToTeam() adds an id from a team to a match object.
     * 
     * @param match
     * @param teamId
     */
    public void addMatchIdToTeam(Match match, long teamId) {
        Team team = teamRepository.findByTeamId(teamId);
        teamRepository.save(team);
    }

    /**
     * getTeamByName returns a list of teams by the same teamName ignoring the case
     * or something similar to it, custom method that's created in "TeamRepository".
     * 
     * @param teamName
     * @return
     */
    public List<Team> getTeamByName(String teamName) {
        List<Team> teams = new ArrayList<>();
        teamRepository.findByTeamNameContainingIgnoreCase(teamName).forEach(teams::add);
        return teams;
    }

    /**
     * findByTeamName() finds a team by it's teamName, custom method that's created
     * in "TeamRepository".
     * 
     * @param teamName
     * @return
     */
    public Team findByTeamName(String teamName) {

        return teamRepository.findByTeamName(teamName);
    }

    /**
     * findTeamById finds a team by it's id, custom method that's created in
     * "TeamRepository".
     * 
     * @param id
     * @return
     */
    public Team findTeamById(long id) {

        return teamRepository.findByTeamId(id);
    }

}
