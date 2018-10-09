package de.adesso.kicker.tournament;

import de.adesso.kicker.user.User;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TournamentService {

    @Autowired
    private TournamentRepository tournamentRepository;

    public TournamentService(TournamentRepository tournamentRepository) {

        this.tournamentRepository = tournamentRepository;
    }

    public TournamentService() {
    }

    /**
     * Sets Tournament.finished to true
     *
     * @param tournament Tournament
     */
    public void setTournamentFinished(Tournament tournament) {

        tournament.setFinished(true);
        tournamentRepository.save(tournament);
    }

    /**
     * Returns a List of Tournaments where finished == false
     *
     * @return List<Tournament>
     */
    public List<Tournament> listRunningTournaments() {

        return tournamentRepository.findByFinished(false);
    }

    /**
     * Returns a list of all tournaments
     *
     * @return tournaments List<Tournaments>
     */
    public List<Tournament> getAllTournaments() {

        List<Tournament> tournaments = new ArrayList<>();
        tournamentRepository.findAll().forEach(tournaments::add);
        return tournaments;
    }

    /**
     * Returns a Tournament with the specified id
     *
     * @param id long
     * @return Tournament
     */
    public Tournament getTournamentById(Long id) {

        return tournamentRepository.findByTournamentId(id);
    }

    /**
     * Saves a Tournament in the Tournament table
     *
     * @param tournament Tournament
     */
    public void saveTournament(Tournament tournament) {

        tournamentRepository.save(tournament);
    }

    /**
     * Adds a player to the list of players that are in the tournament
     *
     * @param tournament Tournament
     * @param player     User
     */
    public void addPlayer(Tournament tournament, User player) {

        tournament.getPlayers().add(player);
        saveTournament(tournament);
    }

}
