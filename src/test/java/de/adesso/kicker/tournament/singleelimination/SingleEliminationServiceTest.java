package de.adesso.kicker.tournament.singleelimination;

import de.adesso.kicker.match.Match;
import de.adesso.kicker.match.MatchDummy;
import de.adesso.kicker.match.MatchService;
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
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SingleEliminationServiceTest {

    @Mock
    TournamentRepository tournamentRepository;

    @Mock
    MatchService matchService;

    @InjectMocks
    SingleEliminationService singleEliminationService;

    private TeamDummy teamDummy = new TeamDummy();
    private SingleElimDummy singleElimDummy = new SingleElimDummy();
    private MatchDummy matchDummy = new MatchDummy();

    private SingleElimination singleElim = singleElimDummy.defaultSingleElim();

    private Team team1 = teamDummy.defaultTeam();
    private Team team2 = teamDummy.alternateTeam();
    private Team team3 = teamDummy.alternateTeam2();
    private Team team4 = teamDummy.alternateTeam3();
    private List<Team> teamsMultiple = new ArrayList<>(Arrays.asList(team1, team2, team3, team4));
    private List<Team> teamsOdd = new ArrayList<>(Arrays.asList(team1, team2, team3));
    private List<Match> bracketEven = new ArrayList<>(Arrays.asList(matchDummy.matchTeam1Team3(), new Match(null, null, null, team2, team4)));
    private List<Match> bracketOdd = Arrays.asList(matchDummy.matchTeam1Team3(), matchDummy.matchTeam2Null());
    private List<Match> bracketAdvancedEven = Collections.singletonList(new Match(null, null, null, team1, team2));


    @BeforeAll
    void setUpAll() {
        MockitoAnnotations.initMocks(this);
        when(tournamentRepository.save(any(SingleElimination.class)))
                .thenAnswer((Answer<SingleElimination>) invocation -> {
                    Object[] args = invocation.getArguments();
                    return (SingleElimination) args[0];
                });

        when(tournamentRepository.save(any(Tournament.class))).thenAnswer((Answer<Tournament>) invocation -> {
            Object[] args = invocation.getArguments();
            return (Tournament) args[0];
        });

        when(matchService.saveMatch(any(Match.class))).thenAnswer((Answer<Match>) invocation -> {
            Object[] args = invocation.getArguments();
            return (Match) args[0];
        });
    }

    @BeforeEach
    void setUp() {
        singleElim = singleElimDummy.defaultSingleElim();
    }

    @Test
    void testCreateTournamentTree_MultipleTwo() {
        List<Team> treeTeamsEven = new ArrayList<>(teamsMultiple);
        singleElim.setTeams(treeTeamsEven);
        singleEliminationService.createTournamentTree(singleElim);
        List<Match> bracket = singleElim.getBracket().get(0).getRow();
        assertEquals(bracket , bracketEven);
    }

    @Test
    void testCreateTournamentTree_Odd() {
        ArrayList<Team> treeTeamsOdd = new ArrayList<>(teamsOdd);
        singleElim.setTeams(treeTeamsOdd);
        singleEliminationService.createTournamentTree(singleElim);
        List<Match> bracket = singleElim.getBracket().get(0).getRow();
        assertEquals(bracket, bracketOdd);
    }

    @Test
    void testAdvanceWinner_Multiple() {
        List<Team> treeTeamsEven = new ArrayList<>(teamsMultiple);
        singleElim.setTeams(treeTeamsEven);
        singleEliminationService.createTournamentTree(singleElim);
        Match match = singleElim.getBracket().get(0).getRow().get(0);
        Match match2 = singleElim.getBracket().get(0).getRow().get(1);
        match.setWinner(team1);
        singleEliminationService.advanceWinner(singleElim, match);
        match2.setWinner(team2);
        singleEliminationService.advanceWinner(singleElim, match2);
        List<Match> bracket = singleElim.getBracket().get(1).getRow();
        assertEquals(bracket, bracketAdvancedEven);
    }

    @Test
    void testAdvanceWinner_Odd() {
        List<Team> treeTeamsOdd = new ArrayList<>(teamsOdd);
        singleElim.setTeams(treeTeamsOdd);
        singleEliminationService.createTournamentTree(singleElim);
        Match match = singleElim.getBracket().get(0).getRow().get(0);
        Match match2 = singleElim.getBracket().get(0).getRow().get(1);
        match.setWinner(team1);
        match2.setWinner(team2);
        singleEliminationService.advanceWinner(singleElim, match);
        singleEliminationService.advanceWinner(singleElim, match2);
        List<Match> bracket = singleElim.getBracket().get(1).getRow();
        assertEquals(bracket, bracketAdvancedEven);

    }

    @Test
    void testAddTeamToTournament() {
        singleEliminationService.joinTournament(singleElim, team1);
        List<Team> tournamentTeams = singleElim.getTeams();
        assertTrue(tournamentTeams.contains(team1));
    }

    @Test
    void testJoinTournament_AlreadyInTournamentException() {
        singleEliminationService.joinTournament(singleElim, team1);
        Assertions.assertThrows(TeamAlreadyInTournamentException.class,
                () -> singleEliminationService.joinTournament(singleElim, team1));
    }

    @Test
    void testJoinTournament_PlayerOfTeamAlreadyInTournamentException() {
        singleEliminationService.joinTournament(singleElim, team3);
        Assertions.assertThrows(PlayerOfTeamInTournamentException.class,
                () -> singleEliminationService.joinTournament(singleElim, team4));
    }
}