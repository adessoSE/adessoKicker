package de.adesso.kicker.tournament.lastmanstanding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.adesso.kicker.tournament.TournamentRepository;
import de.adesso.kicker.tournament.TournamentService;
import de.adesso.kicker.user.User;

@Service
public class LastManStandingService extends TournamentService {

    @Autowired
    public LastManStandingService(TournamentRepository tournamentRepository) {
        super(tournamentRepository);
    }

    @Override
    @PostConstruct
    public void init() {

    }

    public void createLivesMap(LastManStanding lastManStanding) {
        List<User> players = lastManStanding.getPlayers();
        lastManStanding.setRemainingPlayers(new ArrayList<>(players));
        Map<User, Integer> livesMap = lastManStanding.getLivesMap();
        int maxLives = lastManStanding.getMaxLives();
        for (User player : players) {

            livesMap.put(player, maxLives);
        }

        saveTournament(lastManStanding);
    }

    public void decreaseLives(User user, LastManStanding lastManStanding) {

        Map<User, Integer> livesMap = lastManStanding.getLivesMap();
        livesMap.replace(user, livesMap.get(user), livesMap.get(user) - 1);
        if (livesMap.get(user) == 0) {

            lastManStanding.getRemainingPlayers().remove(user);
        }
        checkForWinner(lastManStanding);

    }

    public void joinLastManStanding(LastManStanding lastManStanding, User user) {
        checkForWinner(lastManStanding);
        checkPlayerInTournament(lastManStanding, user);
        addPlayer(lastManStanding, user);
    }

    private void checkForWinner(LastManStanding lastManStanding) {

        List<User> remainingPlayers = lastManStanding.getRemainingPlayers();
        if (remainingPlayers.size() == 1) {
            lastManStanding.setWinner(remainingPlayers.get(0));
            lastManStanding.setFinished(true);
        }
    }

    private void checkPlayerInTournament(LastManStanding lastManStanding, User user) {
        if (lastManStanding.getPlayers().contains(user)) {
            throw new PlayerAlreadyInTournamentException();
        }
    }

}
