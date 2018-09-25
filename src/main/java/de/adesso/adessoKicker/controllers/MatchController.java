package de.adesso.adessoKicker.controllers;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import de.adesso.adessoKicker.objects.Match;
import de.adesso.adessoKicker.services.MatchService;
import de.adesso.adessoKicker.services.TeamService;

@RestController
public class MatchController {

	@Autowired
	private MatchService matchService;
	@Autowired
	private TeamService teamService;
	
	/**
	 * POST all matches on "/matches"
	 * @return
	 */
	@GetMapping("/matches")
	public ModelAndView getAllMatches()
	{
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("matches", matchService.getAllMatches());
		return modelAndView;
	}
	
	/**
	 * gets a match identified by its id
	 * @param id
	 * @return
	 */
	@GetMapping("/matches/{id}")
	public ModelAndView getMatch(@PathVariable long id)
	{
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("match", matchService.getMatchById(id));
		return modelAndView;
	}
	
	/**
	 * ui for adding a match
	 * @return
	 */
	@GetMapping("/matches/add")
	public ModelAndView showMatchCreation() {
		ModelAndView modelAndView = new ModelAndView();
		Match match= new Match();
		modelAndView.addObject("match", match);
		modelAndView.addObject("teams", teamService.getAllTeams());
		modelAndView.setViewName("creatematch");
		return modelAndView;
	    }
	
	/**
     * POST chosen players and create a team with them and add the teamId to the players Team List
     * @param team
     * @param bindingResult
     * @return
     */
    @PostMapping("/matches/add")
    public ModelAndView createNewMatch(@Valid Match match, BindingResult bindingResult)
    {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("creatematch");
        }
        else
        {
            matchService.saveMatch(match);
            teamService.addMatchIdToTeam(match, match.getTeamA().getTeamId());
            teamService.addMatchIdToTeam(match, match.getTeamB().getTeamId());
            modelAndView.addObject("successMessage", "Success: Team has been added.");
            modelAndView.addObject("match", new Match());
            modelAndView.addObject("teams", teamService.getAllTeams());
            modelAndView.setViewName("creatematch");

        }

        return modelAndView;
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
		matchService.saveMatch(match);
	}
	
}
