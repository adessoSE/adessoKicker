package de.adesso.kicker.match;

import de.adesso.kicker.events.MatchCreatedEvent;
import de.adesso.kicker.events.MatchDeclinedEvent;
import de.adesso.kicker.events.MatchVerifiedEvent;
import de.adesso.kicker.match.exception.FutureDateException;
import de.adesso.kicker.match.exception.InvalidCreatorException;
import de.adesso.kicker.match.exception.SamePlayerException;
import de.adesso.kicker.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@Service
public class MatchService {

    private final MatchRepository matchRepository;

    private final UserService userService;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public MatchService(MatchRepository matchRepository, UserService userService,
            ApplicationEventPublisher applicationEventPublisher) {
        this.matchRepository = matchRepository;
        this.userService = userService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void addMatchEntry(Match match) {
        checkForFutureDate(match);
        checkSamePlayer(match);
        checkCurrentUser(match);
        saveMatch(match);
        sendMatchCreationEvent(match);
    }

    private void sendMatchCreationEvent(Match match) {
        MatchCreatedEvent matchCreatedEvent = new MatchCreatedEvent(this, match);
        applicationEventPublisher.publishEvent(matchCreatedEvent);
    }

    @EventListener
    public void verifyMatch(MatchVerifiedEvent matchVerifiedEvent) {
        Match match = matchVerifiedEvent.getMatch();
        match.setVerified(true);
        saveMatch(match);
    }

    @EventListener
    public void declineMatch(MatchDeclinedEvent matchDeclinedEvent) {
        Match match = matchDeclinedEvent.getMatch();
        deleteMatch(match);
    }

    private void deleteMatch(Match match) {
        matchRepository.delete(match);
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