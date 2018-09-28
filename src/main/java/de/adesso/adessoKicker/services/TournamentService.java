package de.adesso.adessoKicker.services;

import de.adesso.adessoKicker.objects.Match;
import de.adesso.adessoKicker.objects.Team;
import de.adesso.adessoKicker.objects.Tournament;
import de.adesso.adessoKicker.objects.User;
import de.adesso.adessoKicker.repositories.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TournamentService {

    private TournamentRepository tournamentRepository;
    
    @Autowired
    public TournamentService(TournamentRepository tournamentRepository) {

        this.tournamentRepository = tournamentRepository;
    }

    /**
     *  Sets Tournament.finished to true
     * @param tournament Tournament
     */
    public void setTournamentFinished(Tournament tournament){

        tournament.setFinished(true);
        tournamentRepository.save(tournament);
    }

    /**
     * Adds a Team to a Tournament
     * @param tournament Tournament
     * @param team Team
     */
    public void addTeamToTournament(Tournament tournament, Team team) {

        tournament.addTeam(team);
        tournamentRepository.save(tournament);
    }

    /**
     * Returns a List of Tournaments where finished == false
     * @return List<Tournament>
     */
    public List<Tournament> listRunningTournaments() {

        return tournamentRepository.findByFinished(false);
    }

    /**
     * Returns a list of all tournaments
     * @return tournaments List<Tournaments>
     */
    public List<Tournament> getAllTournaments() {

        List<Tournament> tournaments = new ArrayList<>();
        tournamentRepository.findAll().forEach(tournaments::add);
        return tournaments;
    }

    /**
     *  Returns a Tournament with the specified id
     * @param id long
     * @return Tournament
     */
    public Tournament getTournamentById(Long id) {

        return tournamentRepository.findByTournamentId(id);
    }

    /**
     *  Saves a Tournament in the Tournament table
     * @param tournament Tournament
     */
    public void saveTournament(Tournament tournament) {

        tournamentRepository.save(tournament);
    }

    /**
     * Adds a player to the list of players that are in the tournament
     * @param tournament Tournament
     * @param player User
     */
    public void addPlayers(Tournament tournament, User player) {

        tournament.getPlayers().add(player);
        saveTournament(tournament);
    }

    /**
     * Creates the tournament tree with the first level of players filled out.
     * If the amount of teams if not a power of 2 the remaining amount needed to get a power of two will be filled
     * with null.
     * Also fills all of the other levels with null so setting players will be easier later
     * @param teams List<Team>
     * @param tournament Tournament
     */

    public void createTournamentTree(List<Team> teams, Tournament tournament) {

        int tournamentSize = (int) Math.pow(2, Math.ceil((Math.log(teams.size()) / Math.log(2))));
        int tournamentTreeSize = (int) (Math.log(tournamentSize) / Math.log(2) + 1);
        List<ArrayList<Team>> tournamentTree = tournament.getTournamentTree();
        Collections.shuffle(teams);

        /*
         * Fills remaining slots with null to fill the tree later
         */
        while (teams.size() < tournamentSize) {
            teams.add(null);
        }

        /*
         * Initializes the tournament tree with the right amount of levels so players can be added easily later
         */
        while (tournamentTree.size() < tournamentTreeSize) {

            tournamentTree.add(new ArrayList<>());
        }

        /*
         * Initializes all places in the List with null to make it easier to set players later
         */
        for (int i = 0; i < tournamentTreeSize; i++) {
            for (int k = tournamentTree.get(i).size(); k < tournamentSize / Math.pow(2, i); k++) {
                tournamentTree.get(i).add(null);
            }
        }

        /*
         * The next two for-loops set the players in the first row of the tournament tree. This is done in an
         * alternating pattern in the case the amount of teams isn't a multiple of two, as then, if we'd just fill it
         * up without alternating there is a chance for a team to get to the finals without playing a single match.
         */
        for (int i = 0; i < tournamentSize; i += 2) {

            tournamentTree.get(0).set(i, teams.get(i));
        }


        for (int i = 1; i < tournamentSize; i += 2) {

            tournamentTree.get(0).set(i, teams.get(i));
        }

        tournament.setTournamentTree(tournamentTree);
    }

    /**
     * Advances the team that is the winner of the specified match
     * @param tournament Tournament
     * @param match Match
     */

    public void advanceWinner(Tournament tournament, Match match) {

        Team winner = match.getWinner();
        List<ArrayList<Team>> tournamentTree = tournament.getTournamentTree();
        boolean winnerSet = false;
        int treeSize = tournamentTree.size();

        for (int i = 0; i < treeSize - 1 && !winnerSet; i++) {
            for (int k = 0; k < tournamentTree.get(i).size() && !winnerSet; k++) {

                if (tournamentTree.get(i).get(k) == winner && !(tournamentTree.get(i + 1).get(k / 2) == winner)) {
                    tournamentTree.get(i + 1).set(k / 2, winner);
                    winnerSet = true;
                }
            }
        }

        saveTournament(tournament);
    }

}
