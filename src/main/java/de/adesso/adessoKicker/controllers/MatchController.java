package de.adesso.adessoKicker.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.adesso.adessoKicker.objects.Match;
import de.adesso.adessoKicker.objects.User;
import de.adesso.adessoKicker.services.MatchService;

@RestController
public class MatchController {

	@Autowired
	private MatchService matchService;
	
	@RequestMapping("/matches")
	public List<Match> getAllMatches()
	{
		return matchService.getAllMatches();
	}
	
	@RequestMapping("/matches/{id}")
	public Match getOneMatch(@PathVariable long id)
	{
		return matchService.getOneMatch(id);
	}
	/*
	@RequestMapping("matches/your")
	public List<Match> getYourMatchesSelf(User user)
	{
		return matchService.getAllMatches();
	}
	*/
	//für admin
	@RequestMapping(method=RequestMethod.POST, value="matches/add")
	public void addMatch(@RequestBody Match match)
	{
		matchService.addMatch(match);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="matches/delete/{id}")
	public void deleteMatch(@PathVariable long id)
	{
		matchService.deleteMatch(id);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="matches/update/{id}")
	public void updateMatch(@RequestBody Match match, @PathVariable long id)
	{
		matchService.updateMatch(match, id);
	}
	
}
