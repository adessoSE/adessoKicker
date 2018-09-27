package de.adesso.adessoKicker.services;

import de.adesso.adessoKicker.objects.Match;
import de.adesso.adessoKicker.objects.Team;
import de.adesso.adessoKicker.objects.Tournament;
import de.adesso.adessoKicker.repositories.MatchRepository;
import de.adesso.adessoKicker.repositories.TeamRepository;
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
    TeamRepository teamRepository;
    MatchRepository matchRepository;



    @Autowired
    public TournamentService(TournamentRepository tournamentRepository, TeamRepository teamRepository, MatchRepository matchRepository) {

        this.tournamentRepository = tournamentRepository;
        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
    }

    private Team tournamentTree[][];

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

        int tournamentSize = (int) Math.pow(2, Math.ceil((Math.log(teams.size()) / Math.log(2))));
        int tournamentTreeSize = (int) (Math.log(tournamentSize) / Math.log(2) + 1);
        List<ArrayList<Team>> tournamentTree = tournament.getTournamentTree();
        Collections.shuffle(teams);

        /**
         * Fills remaining slots with null to fill the tree later
         */
        while (teams.size() < tournamentSize) {
            teams.add(null);
        }

        /**
         * Initializes the tournament tree with the right amount of levels so players can be added easily later
         */
        while (tournamentTree.size() < tournamentTreeSize) {

            tournamentTree.add(new ArrayList<>());
        }

        for (int i = 0; i < tournamentTreeSize; i++) {
            for (int k = tournamentTree.get(i).size(); k < tournamentSize / Math.pow(2, i); k++) {
                tournamentTree.get(i).add(null);
            }
        }

        for (int i = 0; i < tournamentSize; i += 2) {

            tournamentTree.get(0).set(i, teams.get(i));
        }

        for (int i = 1; i < tournamentSize; i += 2) {

            tournamentTree.get(0).set(i, teams.get(i));
        }

        tournament.setTournamentTree(tournamentTree);
        advanceWinner(tournament, new Match());
    }

    public void advanceWinner(Tournament tournament, Match match) {
        match = matchRepository.findMatchByMatchId(9L);
        //tournament.getMatches().add(match);
        match.setWinner(match.getTeamA());
        Team winner = match.getWinner();
        List<ArrayList<Team>> tournamentTree = tournament.getTournamentTree();
        int treeSize = tournamentTree.size();

        for (int i = 0; i < treeSize; i++) {
            for (int k = 0; k < tournamentTree.get(i).size(); k++) {
                if (tournamentTree.get(i).get(k) == winner && !(tournamentTree.get(i + 1).get(k / 2) == winner)) {
                    tournamentTree.get(i + 1).set(k / 2, winner);
                    break;
                }
                break;
            }
            break;
        }
        tournament.setTournamentTree(tournamentTree);
        saveTournament(tournament);
    }

}
