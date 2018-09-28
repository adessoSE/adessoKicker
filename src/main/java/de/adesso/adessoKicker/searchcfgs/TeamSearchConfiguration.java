package de.adesso.adessoKicker.searchcfgs;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.adesso.adessoKicker.services.TeamService;

@EnableAutoConfiguration
@Configuration
public class TeamSearchConfiguration {

	@Autowired
	private EntityManager entityManager;
	
	
	TeamService teamSearchService () {
		//TeamService teamSearchService = new TeamService(entityManager);
		//teamSearchService.initializeTeamSearch();
		//return teamSearchService;
		return null;
	}
}
