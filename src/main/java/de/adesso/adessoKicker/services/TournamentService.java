package de.adesso.adessoKicker.services;

import de.adesso.adessoKicker.objects.Match;
import de.adesso.adessoKicker.objects.Team;
import de.adesso.adessoKicker.objects.Tournament;
import de.adesso.adessoKicker.repositories.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class TournamentService {

    TournamentRepository tournamentRepository;

    @Autowired
    public TournamentService(TournamentRepository tournamentRepository) {

        this.tournamentRepository = tournamentRepository;
    }

    /**
     *  Sets Tournament.finished to true
     */
    public void setTournamentFinished(Tournament tournament){

        tournament.setFinished(true);
        tournamentRepository.save(tournament);
    }

    /**
     * Adds a Team to a Tournament
     */
    public void addTeamToTournament(Tournament tournament, Team team) {

        tournament.addTeam(team);
        tournamentRepository.save(tournament);
    }

    /**
     * Returns a List of Tournaments where finished == false
     */
    public List<Tournament> listRunningTournaments() {

        return tournamentRepository.findByFinished(false);
    }

    public List<Tournament> getAllTournaments() {

        List<Tournament> tournaments = new ArrayList<>();
        tournamentRepository.findAll().forEach(tournaments::add);
        return tournaments;
    }

    /**
     *  Returns a Tournament with the specified id
     */
    public Tournament getTournamentById(Long id) {

        return tournamentRepository.findByTournamentId(id);
    }

    /**
     *  Saves a Tournament in the Tournament table
     */
    public void saveTournament(Tournament tournament) {

        tournamentRepository.save(tournament);
    }

    /***
     * Creates the tournament tree with the first level of playeres filled out.
     * If the amount of teams if not a power of 2 the remaining amount needed to get a power of two will be filled
     * with null.
     * @param teams
     * @param tournament
     */

    public void createTournamentTree(List<Team> teams, Tournament tournament) {

        int tournamentSize = (int) Math.pow(2, Math.ceil(Math.log(teams.size() / Math.log(2))));
        int treeSize = (int) (Math.log(tournamentSize)/Math.log(2) + 1);
        Team tournamentTree[][] = new Team[treeSize][];

        for (int i = 0; i < tournamentTree.length; i++) {

            int roundSize = (int) (tournamentSize / Math.pow(2, i));
            tournamentTree[i] = new Team[roundSize];
        }

        Collections.shuffle(teams);

        while (teams.size() < tournamentSize) {

            teams.add(null);
        }

        for (int i = 0; i < tournamentSize; i += 2) {

            tournamentTree[0][i] = teams.get(i);
        }

        for (int i = 1; i < tournamentSize; i += 2) {

            tournamentTree[0][i] = teams.get(i);
        }

        tournament.setTournamentTree(tournamentTree);
    }

    public void advanceWinner(Tournament tournament, Match match) {

        Team winner = match.getWinner();
        Team treeArray[][] = tournament.getTournamentTree();
        int treeSize = treeArray.length;

        for (int i = 0; i < treeSize; i++) {
            for (int k = 0; k < treeArray[i].length; k++) {

                if (treeArray[i][k] == winner && !(treeArray[i + 1][k / 2] == winner)) {

                    treeArray[i + 1][k / 2] = winner;
                    break;
                }
            }
        }

        tournament.setTournamentTree(treeArray);
        saveTournament(tournament);
    }

}
