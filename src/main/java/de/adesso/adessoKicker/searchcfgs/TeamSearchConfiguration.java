package de.adesso.adessoKicker.searchcfgs;

import javax.persistence.EntityManager;

import de.adesso.adessoKicker.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

import de.adesso.adessoKicker.services.TeamService;

@EnableAutoConfiguration
@Configuration
public class TeamSearchConfiguration {

    private EntityManager entityManager;
    private TeamRepository teamRepository;

    @Autowired
    public TeamSearchConfiguration(EntityManager entityManager, TeamRepository teamRepository) {

        this.entityManager = entityManager;
        this.teamRepository = teamRepository;
    }

    TeamService teamSearchService() {
        TeamService teamSearchService = new TeamService(teamRepository, entityManager);
        teamSearchService.initializeTeamSearch();
        return teamSearchService;
    }
}
