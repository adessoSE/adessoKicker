package de.adesso.adessoKicker.services;

import de.adesso.adessoKicker.objects.Team;
import de.adesso.adessoKicker.objects.Tournament;
import de.adesso.adessoKicker.repositories.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TournamentService {

    TournamentRepository tournamentRepository;

    @Autowired
    public TournamentService(TournamentRepository tournamentRepository) {

        this.tournamentRepository = tournamentRepository;
    }

    public void setTournamentFinished(Tournament tournament){
        tournament.setFinished(true);
        tournamentRepository.save(tournament);
    }

    public void addTeamToTournament(Tournament tournament, Team team) {
        tournament.addTeam(team);
        tournamentRepository.save(tournament);
    }

    public void saveTournament(Tournament tournament) {
        tournamentRepository.save(tournament);
    }

}
