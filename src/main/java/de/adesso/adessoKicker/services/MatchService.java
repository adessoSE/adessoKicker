package de.adesso.adessoKicker.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.adesso.adessoKicker.objects.Match;
import de.adesso.adessoKicker.objects.User;
import de.adesso.adessoKicker.repositories.MatchRepository;

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

    public List<Match> getAllMatchesUser(User user) {

        matches = new ArrayList<>();
        return matches;
    }

    public void saveMatch(Match match) {

        matchRepository.save(match);
    }

    public void deleteMatch(long id) {

        matchRepository.deleteById(id);
    }


}
