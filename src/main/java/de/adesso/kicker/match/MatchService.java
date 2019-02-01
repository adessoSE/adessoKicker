package de.adesso.kicker.match;

import de.adesso.kicker.match.exception.InvalidCreatorException;
import de.adesso.kicker.match.exception.SamePlayerMatchExeption;
import de.adesso.kicker.match.exception.SamePlayerTeamException;
import de.adesso.kicker.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public Match addMatchEntry(Match match) {
        checkSamePlayerMatch(match);
        checkSamePlayerTeam(match);
        checkCurrentUser(match);
        return matchRepository.save(match);
    }

    public Match getMatchById(long id) {
        return matchRepository.findByMatchId(id);
    }

    public Match saveMatch(Match match) {
        return matchRepository.save(match);
    }

    public void deleteMatch(long id) {
        matchRepository.deleteById(id);
    }

    private void checkSamePlayerMatch(Match match) {
        if (match.getTeamAPlayer1().equals(match.getTeamBPlayer1())
                || Objects.equals(match.getTeamAPlayer1(), match.getTeamBPlayer2())
                || Objects.equals(match.getTeamBPlayer1(), match.getTeamAPlayer2())
                || (Objects.equals(match.getTeamAPlayer2(), match.getTeamBPlayer2())
                && match.getTeamAPlayer2() != null)) {
            throw new SamePlayerMatchExeption();
        }
    }

    private void checkSamePlayerTeam(Match match) {
        if (Objects.equals(match.getTeamAPlayer1(), match.getTeamAPlayer2())
                || Objects.equals(match.getTeamBPlayer1(), match.getTeamBPlayer2())) {
            throw new SamePlayerTeamException();
        }
    }

    private void checkCurrentUser(Match match) {
        if (match.getTeamAPlayer1().equals(userService.getLoggedInUser())) {
            throw new InvalidCreatorException();
        }
    }
}