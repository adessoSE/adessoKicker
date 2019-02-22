package de.adesso.kicker.match;

import de.adesso.kicker.match.exception.FutureDateException;
import de.adesso.kicker.match.exception.InvalidCreatorException;
import de.adesso.kicker.match.exception.SamePlayerException;
import de.adesso.kicker.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
        if (match.getTeamAPlayer1().equals(match.getTeamBPlayer1())) {
            throw new SamePlayerException();
        }
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

    private void checkForFutureDate(Match match) {
        if (match.getDate().isAfter(LocalDate.now())) {
            throw new FutureDateException();
        }
    }
}