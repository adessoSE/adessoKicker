package de.adesso.adessoKicker.services;

import de.adesso.adessoKicker.objects.Tournament;
import de.adesso.adessoKicker.repositories.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class TournamentService {

    TournamentRepository tournamentRepository;

    @Autowired
    public TournamentService(TournamentRepository tournamentRepository) {

        this.tournamentRepository = tournamentRepository;

    }

    public void setTournamentFinished(Tournament tournament){

    }

    public void saveTournament(Tournament tournament) {
        tournamentRepository.save(tournament);
    }

}
