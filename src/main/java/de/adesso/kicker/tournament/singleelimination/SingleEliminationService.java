package de.adesso.kicker.tournament.singleelimination;

import java.util.List;
import javax.annotation.PostConstruct;
import de.adesso.kicker.tournament.singleelimination.exception.PlayerInTournamentException;
import de.adesso.kicker.tournament.singleelimination.exception.PlayerOfTeamInTournamentException;
import de.adesso.kicker.tournament.singleelimination.exception.TeamAlreadyInTournamentException;
import de.adesso.kicker.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import de.adesso.kicker.match.Match;
import de.adesso.kicker.match.MatchService;
import de.adesso.kicker.team.Team;
import de.adesso.kicker.tournament.TournamentRepository;
import de.adesso.kicker.tournament.TournamentService;
import de.adesso.kicker.user.User;

@Service
public class SingleEliminationService extends TournamentService {

    private MatchService matchService;
    private UserService userService;

    @Autowired
    public SingleEliminationService(TournamentRepository tournamentRepository, MatchService matchService,
            UserService userService) {
        super(tournamentRepository);
        this.matchService = matchService;
        this.userService = userService;
    }

    public void addTeamToTournament(SingleElimination singleElimination, Team team) {
        singleElimination.addTeam(team);
        saveTournament(singleElimination);
    }

    @Override
    @PostConstruct
    public void init() {
    }

    /**
     * Creates the tournament tree with the first level of players filled out. If
     * the amount of teams if not a power of 2 the remaining amount needed to get a
     * power of two will be filled with null. Also fills all of the other levels
     * with null so setting players will be easier later
     *
     * @param singleElimination SingleElimination
     */
    @Transactional
    public void createTournamentTree(SingleElimination singleElimination) {

        List<Team> teams = singleElimination.getTeams();
        int tournamentSize = (int) Math.pow(2,
                Math.ceil((Math.log(singleElimination.getTeams().size()) / Math.log(2))));
        int tournamentTreeSize = (int) (Math.log(tournamentSize) / Math.log(2) + 1);
        List<BracketRow> tournamentTree = singleElimination.getBracket();

        /* Fills remaining slots with null to fill the tree later */
        while (teams.size() < tournamentSize) {

            teams.add(null);
        }

        /*
         * Initializes the tournament tree with the right amount of levels so matches
         * can be added easily later
         */
        while (tournamentTree.size() < tournamentTreeSize - 1) {

            tournamentTree.add(new BracketRow());
        }

        /*
         * Initializes all places in the List with null to make it easier to set matches
         * later
         */
        for (int i = 0; i < tournamentTreeSize - 1; i++) {
            for (int k = tournamentTree.get(i).getRow().size(); k < (tournamentSize / Math.pow(2, i)) / 2; k++) {
                Match match = new Match();
                matchService.saveMatch(match);
                tournamentTree.get(i).getRow().add(match);
            }
        }

        /*
         * The next two for-loops set the players in the first row of the tournament
         * tree. This is done in an alternating pattern in the case the amount of teams
         * isn't a multiple of two, as then, if we'd just fill it up without alternating
         * there is a chance for a team to get to the finals without playing a single
         * match.
         */
        for (int i = 0; i < tournamentSize / 2; i++) {
            tournamentTree.get(0).getRow().get(i).setTeamA(teams.get(i));
            tournamentTree.get(0).getRow().get(i).setTeamB(teams.get(i + tournamentSize / 2));
        }

        saveTournament(singleElimination);
    }

    public void createSingleEliminationTournament(SingleElimination singleElimination) {
        saveTournament(singleElimination);
    }

    @Transactional
    public void joinTournament(SingleElimination singleElimination, Team team) {
        checkTeamInTournament(singleElimination, team);
        checkPlayerOfTeamInTournament(singleElimination, team);
        // tournamentJoinRequestService.saveTournamentJoinRequest((Tournament)
        // singleElimination, userService.getLoggedInUser().getUserId(), team);
    }

    public void getTournamentPage(SingleElimination singleElimination, User loggedInUser) {
        checkPlayerInTournament(singleElimination, loggedInUser);
    }

    /**
     * Advances the team that is the winner of the specified match
     *
     * @param singleElimination SingleElimination
     * @param match             Match
     */
    public void advanceWinner(SingleElimination singleElimination, Match match) {

        Team winner = match.getWinner();
        List<BracketRow> tournamentTree = singleElimination.getBracket();
        boolean winnerSet = false;
        int treeSize = tournamentTree.size();

        for (int i = 0; i < treeSize - 1 && !winnerSet; i++) {
            for (int k = 0; k < tournamentTree.get(i).getRow().size() && !winnerSet; k++) {
                if (tournamentTree.get(i).getRow().get(k).getWinner() == winner
                        && !(tournamentTree.get(i + 1).getRow().get(k / 2).getWinner() == winner)) {

                    if (tournamentTree.get(i + 1).getRow().get(k / 2).getTeamA() == null) {
                        tournamentTree.get(i + 1).getRow().get(k / 2).setTeamA(winner);
                    } else {
                        tournamentTree.get(i + 1).getRow().get(k / 2).setTeamB(winner);
                    }
                    winnerSet = true;
                }
            }
        }
        saveTournament(singleElimination);
    }

    private void checkTeamInTournament(SingleElimination singleElimination, Team team) {

        if (singleElimination.getTeams().contains(team)) {
            throw new TeamAlreadyInTournamentException();
        }
    }

    private void checkPlayerInTournament(SingleElimination singleElimination, User player) {
        List<User> players = singleElimination.getPlayers();
        if (players.contains(player)) {
            throw new PlayerInTournamentException();
        }
    }

    private void checkPlayerOfTeamInTournament(SingleElimination singleElimination, Team team) {
        List<User> players = singleElimination.getPlayers();
        if (players.contains(team.getPlayerA()) || players.contains(team.getPlayerB())) {
            throw new PlayerOfTeamInTournamentException();
        }
    }
}
