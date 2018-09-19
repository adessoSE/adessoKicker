package de.adesso.adessoKicker.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.adesso.adessoKicker.objects.Match;
import de.adesso.adessoKicker.objects.User;
import de.adesso.adessoKicker.repositories.MatchRepository;
import de.adesso.adessoKicker.repositories.UserRepository;

@Service
public class MatchService {

	@Autowired
	private MatchRepository matchRepository;
	
	private List<Match> matches;
	
	public List<Match> getAllMatches()
	{
		matches = new ArrayList<Match>();
		matchRepository.findAll().forEach(matches::add);
		return matches;
	}
	
	public Match getOneMatch(long id)
	{
		return matchRepository.findById(id).get();
	}
	
	public List<Match> getAllMatchesSelf(User user)
	{
		matches = new ArrayList<Match>();
		return matches;
				
	}
	
	public void addMatch(Match match)
	{
		matchRepository.save(match);
	}
	
	public void deleteMatch(long id)
	{
		matchRepository.deleteById(id);
	}
	
	public void updateMatch(Match match, long id)
	{
		matchRepository.save(matchRepository.findById(id).get());
	}
	

	
}
