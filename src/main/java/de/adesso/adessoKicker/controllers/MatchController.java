package de.adesso.adessoKicker.controllers;

/**
 * 	Controller managing Matches
 * @author caylak
 */

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.adesso.adessoKicker.objects.Match;
import de.adesso.adessoKicker.services.MatchService;

@RestController
public class MatchController {

	@Autowired
	private MatchService matchService;
	
	/**
	 * gets all matches
	 * @return
	 */
	@RequestMapping("/matches")
	public List<Match> getAllMatches()
	{
		return matchService.getAllMatches();
	}
	
	/**
	 * gets a match identified by its id
	 * @param id
	 * @return
	 */
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
	
	/**
	 * adds a match to the database
	 * @param match
	 */
	@RequestMapping(method=RequestMethod.POST, value="matches/add")
	public void addMatch(@RequestBody Match match)
	{
		matchService.addMatch(match);
	}
	
	/**
	 * deletes a match from the database identified by an id
	 * @param id
	 */
	@RequestMapping(method=RequestMethod.DELETE, value="matches/delete/{id}")
	public void deleteMatch(@PathVariable long id)
	{
		matchService.deleteMatch(id);
	}
	
	
	/**
	 * updates an existing match by the actual object and its id
	 * @param match
	 * @param id
	 */
	@RequestMapping(method=RequestMethod.PUT, value="matches/update/{id}")
	public void updateMatch(@RequestBody Match match, @PathVariable long id)
	{
		matchService.updateMatch(match, id);
	}
	
}
