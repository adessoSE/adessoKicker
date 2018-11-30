package de.adesso.kicker.match;

import de.adesso.kicker.user.User;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service that handles "MatchService" used in "MatchController".
 */

@Service
public class MatchService {

    private MatchRepository matchRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository) {

        this.matchRepository = matchRepository;
    }

    private List<Match> matches;

    /**
     * getAllMatches() returns a list of all matches.
     * 
     * @return
     */
    public List<Match> getAllMatches() {

        matches = new ArrayList<>();
        matchRepository.findAll().forEach(matches::add);
        return matches;
    }

    /**
     * getMatchById() returns an unique match.
     * 
     * @param id
     * @return
     */
    public Match getMatchById(long id) {

        return matchRepository.findByMatchId(id);
    }

    /**
     * getAllMatchesByUser() returns a list of all matches from an user.
     * 
     * @param user
     * @return
     */
    public List<Match> getAllMatchesByUser(User user) {

        matches = new ArrayList<>();
        return matches;
    }

    /**
     * saveMatch() saves a match object.
     * 
     * @param match
     */
    public Match saveMatch(Match match) {

        return matchRepository.save(match);
    }

    /**
     * deleteMatch() deletes an unique match by it's id.
     * 
     * @param id
     */
    public void deleteMatch(long id) {

        matchRepository.deleteById(id);
    }

    public void denyPastDate(Match match) {
        Date currentDate = new Date();
        if (match.getDate().after(yesterday()) && (match.getTime().getHours() >= currentDate.getHours()
                && match.getTime().getMinutes() >= currentDate.getMinutes())) {
        } else {
            throw new PasteDateException();
        }

    }

    public void identicalTeams(Match match) {
        if (match.getTeamA().getTeamId() == match.getTeamB().getTeamId()) {
            throw new IdenticalTeamsException();
        }
    }

    private Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

}