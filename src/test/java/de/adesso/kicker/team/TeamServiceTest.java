package de.adesso.kicker.team;

import de.adesso.kicker.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TeamServiceTest {

    @Mock
    TeamRepository teamRepository;

    @InjectMocks
    TeamService teamService;

    private TeamDummy teamDummy = new TeamDummy();

    private Team team = teamDummy.defaultTeam();
    private Team otherTeam = teamDummy.alternateTeam();

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);

        when(teamRepository.findAll()).thenReturn(Arrays.asList(team, otherTeam));

        when(teamRepository.save(any(Team.class))).thenAnswer((Answer<Team>) invocation -> {
            Object[] args = invocation.getArguments();
            return (Team) args[0];
        });

        when(teamRepository.findByTeamId(anyLong())).thenReturn(null);
        when(teamRepository.findByTeamId(team.getTeamId())).thenReturn(team);

        when(teamRepository.findByTeamName(anyString())).thenReturn(null);
        when(teamRepository.findByTeamName(eq(team.getTeamName()))).thenReturn(team);

    }

    @Test
    void testGetAllTeams() {
        ArrayList<Team> allTeams = new ArrayList<>();
        teamService.getAllTeams().forEach(allTeams::add);
        assertTrue(allTeams.contains(team));
        assertTrue(allTeams.contains(otherTeam));
    }

    @Test
    void testGetTeamById() {
        Team teamById = teamService.findTeamById(team.getTeamId());
        assertEquals(team, teamById);
    }

    @Test
    void testSaveTeam() {
        Team savedTeam = teamService.saveTeam(team);
        verify(teamRepository).save(team);
        assertEquals(team, savedTeam);
    }

    @Test
    void findByTeamName() {
        Team teamByName = teamService.findByTeamName(team.getTeamName());
        assertEquals(teamByName, team);
    }

    @Test
    void findTeamById() {
    }
}