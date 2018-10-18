package de.adesso.kicker.team;

import de.adesso.kicker.match.Match;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeamService {

    private TeamRepository teamRepository;
    @Autowired
    public TeamService(TeamRepository teamRepository, EntityManager entityManager) {
        this.teamRepository = teamRepository;
    }

    public List<Team> getAllTeams() {

        List<Team> teams = new ArrayList<>();
        teamRepository.findAll().forEach(teams::add);
        return teams;
    }

    public Team getTeamById(long id) {

        return teamRepository.findByTeamId(id);
    }

    public void saveTeam(Team team) {

        teamRepository.save(team);
    }

    public void deleteTeam(long id) {

        teamRepository.deleteById(id);
    }

    public Team findByTeamName(String teamName) {

        return teamRepository.findByTeamName(teamName);
    }

    public Team findTeamByTeamId(long id) {

        return teamRepository.findByTeamId(id);
    }

    public void addMatchIdToTeam(Match match, long teamId) {
        Team team = teamRepository.findByTeamId(teamId);
        teamRepository.save(team);
    }

    public List<Team> getTeamByName(String teamName) {
        List<Team> teams = new ArrayList<>();
        teamRepository.findByTeamNameContainingIgnoreCase(teamName).forEach(teams::add);
        return teams;
    }

}
