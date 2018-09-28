package de.adesso.adessoKicker.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.adesso.adessoKicker.objects.Match;
import de.adesso.adessoKicker.objects.Team;
import de.adesso.adessoKicker.repositories.TeamRepository;

@Service
public class TeamService {

	private TeamRepository teamRepository;
	private final EntityManager entityManager;
	
	@Autowired
	public TeamService(TeamRepository teamRepository, EntityManager entityManager) {
		this.teamRepository = teamRepository;
        this.entityManager = entityManager;
    }
/*
    public TeamService(EntityManager entityManager) {
	    this.entityManager = entityManager;
    }
	*/
	public void initializeTeamSearch() {
		try {
			FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
			fullTextEntityManager.createIndexer().startAndWait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public List<Team> teamSearch(String searchTerm) {
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Team.class).get();
	
		Query luceneQuery =	queryBuilder.keyword().fuzzy().withEditDistanceUpTo(1).onField("teamName").matching(searchTerm).createQuery();
		javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Team.class);
		
	
	//execute search 
	List<Team> TeamList = null;
	try {
		TeamList = jpaQuery.getResultList();
	} catch (NoResultException nre) {
		// do nothing
	
	}
	
		return TeamList;
	}
	
	public List<Team> getAllTeams()
	{

		List<Team> teams = new ArrayList<>();
		teamRepository.findAll().forEach(teams::add);
		return teams;
	}
	
	public Team getTeamById(long id)
	{

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

	public Team findTeamByUserId(long id) {

	    return new Team();
    }
	
	public void addMatchIdToTeam(Match match, long teamId)
	{
		Team team = teamRepository.findByTeamId(teamId);
		teamRepository.save(team);
	}
}
