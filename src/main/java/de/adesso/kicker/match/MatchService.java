package de.adesso.kicker.match;

import de.adesso.kicker.match.exception.PastDateException;
import de.adesso.kicker.user.User;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchService {

    private MatchRepository matchRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository) {

        this.matchRepository = matchRepository;
    }

    private List<Match> matches;

    public List<Match> getAllMatches() {

        matches = new ArrayList<>();
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

        matchRepository.deleteById(id);
    }

    public void denyPastDate(Match match) {
        Date currentDate = new Date();
        if (match.getDate().after(currentDate)) {
        } else {
            throw new PastDateException();
        }

    }

    private Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

}