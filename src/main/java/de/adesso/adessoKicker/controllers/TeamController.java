package de.adesso.adessoKicker.controllers;

import java.util.ArrayList;
import java.util.List;

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

import de.adesso.adessoKicker.objects.Team;
import de.adesso.adessoKicker.objects.User;
import de.adesso.adessoKicker.services.TeamService;

@RestController
public class TeamController {

	@Autowired
	TeamService teamService;
	
	@RequestMapping("/teams")
	public List<Team> getAllTeams()
	{
		List<Team> allTeams = new ArrayList<Team>();
		teamService.getAllTeams().forEach(allTeams::add);
		return allTeams;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/teams/{id}")
	public Team getOneTeam(@PathVariable long id)
	{
		
		return teamService.getOneTeam(id);
	}
	
	/*
	@RequestMapping(method=RequestMethod.POST, value="/teams/add")
	public void addTeam(Team team)
	{
		teamService.addTeam(team);
	}
	*/
	@GetMapping("/teams/add")
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        Team team = new Team();
        modelAndView.addObject("team", team);
        modelAndView.setViewName("addteam");
        return modelAndView;
    }
	
	@PostMapping("/teams/add")
	public ModelAndView createNewTeam(@Valid Team team, BindingResult bindingResult)
	{
		ModelAndView modelAndView = new ModelAndView();
		Team teamExists = teamService.findByTeamName(team.getTeamName());
		if (teamExists != null) {
			bindingResult.rejectValue("teamName", "error.teamName", "Fail: Team Name already exists.");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("addteam");
		}
		else
		{
			teamService.addTeam(team);
			modelAndView.addObject("successMessage", "Success: Team has been added.");
			modelAndView.addObject("team", new Team());
			modelAndView.setViewName("addteam");
		
		}
		
		return modelAndView;
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/teams/delete/{id}")
	public void deleteTeam(@PathVariable long id)
	{
		teamService.deleteTeam(id);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/teams/update/{id}")
	public void updateTeam(@RequestBody Team team, @PathVariable long id)
	{
		teamService.updateTeam(team, id);
	}
}
