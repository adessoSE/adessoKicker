package de.adesso.kicker.tournament.singleelimination;

import de.adesso.kicker.team.Team;
import de.adesso.kicker.tournament.TournamentRepository;
import de.adesso.kicker.tournament.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SingleEliminationService extends TournamentService {

    @Autowired
    public SingleEliminationService(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public void addTeamToTournament(SingleElimination singleElimination, Team team) {
        singleElimination.addTeam(team);
        tournamentRepository.save(singleElimination);
    }
}
