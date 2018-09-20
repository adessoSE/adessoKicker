package de.adesso.adessoKicker.services;

import de.adesso.adessoKicker.objects.Team;
import de.adesso.adessoKicker.objects.Tournament;
import de.adesso.adessoKicker.repositories.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     *  Returns a Tournament with the specified id
     */
    public Tournament returnTournament(Long id) {

        return tournamentRepository.findByTournamentId(id);
    }

    /**
     *  Saves a Tournament in the Tournament table
     */
    public void saveTournament(Tournament tournament) {
        tournamentRepository.save(tournament);
    }

}
