package de.adesso.kicker.match;

import de.adesso.kicker.match.exception.FutureDateException;
import de.adesso.kicker.match.exception.InvalidCreatorException;
import de.adesso.kicker.match.exception.SamePlayerException;
import de.adesso.kicker.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@Service
public class MatchService {

    private final MatchRepository matchRepository;

    private final UserService userService;

    @Autowired
    public MatchService(MatchRepository matchRepository, UserService userService) {
        this.matchRepository = matchRepository;
        this.userService = userService;
    }

    public void addMatchEntry(Match match) {
        checkForFutureDate(match);
        checkSamePlayer(match);
        checkCurrentUser(match);
        saveMatch(match);
    }

    public void verifyMatch(Match match) {
        match.setVerified(true);
        saveMatch(match);
    }

    private void saveMatch(Match match) {
        matchRepository.save(match);
    }

    private void checkSamePlayer(Match match) {
        if (checkSamePlayerInTeam(match)) {
            throw new SamePlayerException();
        }
        if (checkSamePlayer1InBothTeams(match) || checkSamePlayer2InBothTeams(match)) {
            throw new SamePlayerException();
        }
        if (checkSamePlayerInDifferentTeams(match)) {
            throw new SamePlayerException();
        }
    }

    private void checkCurrentUser(Match match) {
        if (!match.getTeamAPlayer1().equals(userService.getLoggedInUser())) {
            throw new InvalidCreatorException();
        }
    }

    private void checkForFutureDate(Match match) {
        if (match.getDate().isAfter(LocalDate.now())) {
            throw new FutureDateException();
        }
    }

    private boolean checkSamePlayer1InBothTeams(Match match) {
        return match.getTeamAPlayer1().equals(match.getTeamBPlayer1());
    }

    private boolean checkSamePlayerInTeam(Match match) {
        return (Objects.equals(match.getTeamAPlayer1(), match.getTeamAPlayer2())
                || Objects.equals(match.getTeamBPlayer1(), match.getTeamBPlayer2()));
    }

    private boolean checkSamePlayerInDifferentTeams(Match match) {
        return Objects.equals(match.getTeamAPlayer1(), match.getTeamBPlayer2())
                || Objects.equals(match.getTeamBPlayer1(), match.getTeamAPlayer2());
    }

    private boolean checkSamePlayer2InBothTeams(Match match) {
        return (Objects.equals(match.getTeamAPlayer2(), match.getTeamBPlayer2())
                && (match.getTeamBPlayer2() != null || match.getTeamAPlayer2() != null));
    }

}