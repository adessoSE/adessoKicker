package de.adesso.kicker.match;

import de.adesso.kicker.match.exception.InvalidCreatorException;
import de.adesso.kicker.match.exception.MatchNotFoundException;
import de.adesso.kicker.match.exception.SamePlayerException;
import de.adesso.kicker.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchService {

    private MatchRepository matchRepository;
    private UserService userService;

    @Autowired
    public MatchService(MatchRepository matchRepository, UserService userService) {
        this.matchRepository = matchRepository;
        this.userService = userService;
    }

    public List<Match> getAllMatches() {
        var matches = new ArrayList<Match>();
        matchRepository.findAll().forEach(matches::add);
        return matches;
    }

    public Match getMatchById(long id) {

        return matchRepository.findByMatchId(id);
    }

    public List<Match> getAllMatchesByUser(User user) {

        matches = new ArrayList<>();
        return matches;
    }

    public Match saveMatch(Match match) {

        return matchRepository.save(match);
    }

    public void deleteMatch(long id) {

        if (Objects.equals(match.getTeamAPlayer1(), match.getTeamAPlayer2())
                || Objects.equals(match.getTeamBPlayer1(), match.getTeamBPlayer2())) {
            throw new SamePlayerException();
        }
        if (Objects.equals(match.getTeamAPlayer1(), match.getTeamBPlayer2())
                || Objects.equals(match.getTeamBPlayer1(), match.getTeamAPlayer2())
                || (Objects.equals(match.getTeamAPlayer2(), match.getTeamBPlayer2())
                        && (match.getTeamBPlayer2() != null || match.getTeamAPlayer2() != null))) {
            throw new SamePlayerException();
        }
    }

    private void checkCurrentUser(Match match) {
        if (!match.getTeamAPlayer1().equals(userService.getLoggedInUser())) {
            throw new InvalidCreatorException();
        }
    }

    private void checkMatchExists(Match match) {
        if (match == null) {
            throw new MatchNotFoundException();
        }
    }
}