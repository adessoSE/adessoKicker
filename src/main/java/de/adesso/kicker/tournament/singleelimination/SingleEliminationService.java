package de.adesso.kicker.tournament.singleelimination;

import de.adesso.kicker.match.Match;
import de.adesso.kicker.team.Team;
import de.adesso.kicker.tournament.TournamentRepository;
import de.adesso.kicker.tournament.TournamentService;
import de.adesso.kicker.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SingleEliminationService extends TournamentService {

    private TournamentRepository tournamentRepository;

    @Autowired
    public SingleEliminationService(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public void addTeamToTournament(SingleElimination singleElimination, Team team) {
        singleElimination.addTeam(team);
        singleElimination.getPlayers().add(team.getPlayerA());
        singleElimination.getPlayers().add(team.getPlayerB());
        tournamentRepository.save(singleElimination);
    }

    /**
     * Creates the tournament tree with the first level of players filled out. If
     * the amount of teams if not a power of 2 the remaining amount needed to get a
     * power of two will be filled with null. Also fills all of the other levels
     * with null so setting players will be easier later
     *
     * @param teams             List<Team>
     * @param singleElimination SingleElimination
     */

    public void createTournamentTree(List<Team> teams, SingleElimination singleElimination) {

        int tournamentSize = (int) Math.pow(2, Math.ceil((Math.log(teams.size()) / Math.log(2))));
        int tournamentTreeSize = (int) (Math.log(tournamentSize) / Math.log(2) + 1);
        List<BracketRow> tournamentTree = singleElimination.getBracket();
        Collections.shuffle(teams);

        /* Fills remaining slots with null to fill the tree later */
        while (teams.size() < tournamentSize) {

            teams.add(null);
        }

        /*
         * Initializes the tournament tree with the right amount of levels so players
         * can be added easily later
         */
        while (tournamentTree.size() < tournamentTreeSize) {

            tournamentTree.add(new BracketRow());
        }

        /*
         * Initializes all places in the List with null to make it easier to set players
         * later
         */
        for (int i = 0; i < tournamentTreeSize; i++) {
            for (int k = tournamentTree.get(i).getRow().size(); k < tournamentSize / Math.pow(2, i); k++) {

                tournamentTree.get(i).getRow().add(null);
            }
        }

        /*
         * The next two for-loops set the players in the first row of the tournament
         * tree. This is done in an alternating pattern in the case the amount of teams
         * isn't a multiple of two, as then, if we'd just fill it up without alternating
         * there is a chance for a team to get to the finals without playing a single
         * match.
         */
        for (int i = 0; i < tournamentSize; i += 2) {

            tournamentTree.get(0).getRow().set(i, teams.get(i));
        }

        for (int i = 1; i < tournamentSize; i += 2) {

            tournamentTree.get(0).getRow().set(i, teams.get(i));
        }

        tournamentRepository.save(singleElimination);
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
                if (tournamentTree.get(i).getRow().get(k) == winner
                        && !(tournamentTree.get(i + 1).getRow().get(k / 2) == winner)) {
                    tournamentTree.get(i + 1).getRow().set(k / 2, winner);
                    winnerSet = true;
                }
            }
        }

        tournamentRepository.save(singleElimination);
    }

    public void checkTeamInTournament(SingleElimination singleElimination, Team team) {

        if(singleElimination.getTeams().contains(team)) {
            throw new TeamAlreadyInTournamentException();
        }
    }

    public void checkPlayerTeamInTournament(SingleElimination singleElimination, Team team) {
        List<User> players = singleElimination.getPlayers();
        if(players.contains(team.getPlayerA()) || players.contains(team.getPlayerB())) {
            throw new PlayerOfTeamAlreadyInTournamentException();
        }
    }
}
