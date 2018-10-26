package de.adesso.kicker.tournament.singleelimination;

import de.adesso.kicker.match.Match;
import de.adesso.kicker.match.MatchDummy;
import de.adesso.kicker.team.Team;
import de.adesso.kicker.team.TeamDummy;
import de.adesso.kicker.tournament.Tournament;
import de.adesso.kicker.tournament.TournamentRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SingleEliminationServiceTest {

    @Mock
    TournamentRepository tournamentRepository;

    @InjectMocks
    SingleEliminationService singleEliminationService;

    private TeamDummy teamDummy = new TeamDummy();
    private SingleElimDummy singleElimDummy = new SingleElimDummy();
    private MatchDummy matchDummy = new MatchDummy();

    private SingleElimination singleElim = singleElimDummy.defaultSingleElim();

    private Team team1 = teamDummy.defaultTeam();
    private Team team2 = teamDummy.alternateTeam();
    private Team team3 = teamDummy.alternateTeam2();
    private ArrayList<Team> teamsMultiple = new ArrayList<>(Arrays.asList(team1, team2));
    private ArrayList<Team> teamsOdd = new ArrayList<>(Arrays.asList(team1, team2, team3));

    private Match match = matchDummy.defaultMatch();
    private Match matchWithNull = matchDummy.matchWithNull();

    @BeforeAll
    void setUpAll() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    void setUp() {

        when(tournamentRepository.save(any(SingleElimination.class))).thenAnswer((Answer<SingleElimination>) invocation -> {
            Object[] args = invocation.getArguments();
            return (SingleElimination) args[0];
        });

        when(tournamentRepository.save(any(Tournament.class))).thenAnswer((Answer<Tournament>) invocation -> {
            Object[] args = invocation.getArguments();
            return (Tournament) args[0];
        });

    }

    @Test
    void testAddTeamToTournament() {
        singleEliminationService.addTeamToTournament(singleElim, team1);
        List<Team> tournamentTeams = singleElim.getTeams();
        assertTrue(tournamentTeams.contains(team1));
    }

    @Test
    void testCreateTournamentTree_MultipleTwo() {
        ArrayList<Team> treeTeamsEven = new ArrayList<>(teamsMultiple);
        singleEliminationService.createTournamentTree(treeTeamsEven, singleElim);
        for(int i = 0; i < treeTeamsEven.size(); i++) {
            assertTrue(treeTeamsEven.contains(teamsMultiple.get(i)));
        }
    }

    @Test
    void testCreateTournamentTree_Odd() {
        ArrayList<Team> treeTeamsOdd = new ArrayList<>(teamsOdd);
        int nullSpots = 0;
        int treeSize = (int) Math.pow(2, Math.ceil((Math.log(treeTeamsOdd.size()) / Math.log(2))));
        int nullSpotSize = treeSize - treeTeamsOdd.size();
        ArrayList<Team> afterTeams = new ArrayList<>();
        singleEliminationService.createTournamentTree(treeTeamsOdd, singleElim);
        for(int i = 0; i < treeTeamsOdd.size(); i++) {
            if (treeTeamsOdd.get(i) == null) {
                nullSpots++;
            } else {
                afterTeams.add(treeTeamsOdd.get(i));
            }
        }
        for(int i = 0; i < teamsOdd.size(); i++) {
            assertTrue(afterTeams.contains(treeTeamsOdd.get(i)));
        }
        assertEquals(nullSpots, nullSpotSize);
    }

    @Test
    void testAdvanceWinner_Multiple() {
        match.setWinner(team1);
        testCreateTournamentTree_MultipleTwo();
        singleEliminationService.advanceWinner(singleElim, match);
        assertTrue(singleElim.getBracket().get(1).getRow().contains(team1));
    }

    @Test
    void testAdvanceWinner_Odd() {
        match.setWinner(team2);
        testCreateTournamentTree_Odd();
        singleEliminationService.advanceWinner(singleElim, match);
        assertTrue(singleElim.getBracket().get(1).getRow().contains(team2));
        singleEliminationService.advanceWinner(singleElim, match);
        assertTrue(singleElim.getBracket().get(2).getRow().contains(team2));

    }

    @Test
    void testCheckTeamInTournament() {
        singleEliminationService.addTeamToTournament(singleElim, team1);
        Assertions.assertThrows(TeamAlreadyInTournamentException.class, () -> {
            singleEliminationService.checkTeamInTournament(singleElim, team1);
        });
    }

    @Test
    void checkPlayerTeamInTournament() {
        singleEliminationService.addTeamToTournament(singleElim, team1);
        Assertions.assertThrows(PlayerOfTeamAlreadyInTournamentException.class, () -> {
            singleEliminationService.checkPlayerTeamInTournament(singleElim, team1);
        });
    }
}