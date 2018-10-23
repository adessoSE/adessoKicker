package de.adesso.kicker.match;

import de.adesso.kicker.user.User;
import java.util.ArrayList;
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
    public void saveMatch(Match match) {

        matchRepository.save(match);
    }

    /**
     * deleteMatch() deletes an unique match by it's id.
     * 
     * @param id
     */
    public void deleteMatch(long id) {

        matchRepository.deleteById(id);
    }
}
