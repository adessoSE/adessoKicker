package de.adesso.adessoKicker.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import de.adesso.adessoKicker.services.UserService;
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
import de.adesso.adessoKicker.repositories.UserRepository;
import de.adesso.adessoKicker.services.TeamService;

/**
 * Controller managing Teams
 * @author caylak
 *
 */
@RestController
public class TeamController {

    @Autowired
    TeamService teamService;
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    /**
     * gets all teams
     * @return
     */
    @RequestMapping("/teams")
    public List<Team> getAllTeams()
    {
        List<Team> allTeams = new ArrayList<Team>();
        teamService.getAllTeams().forEach(allTeams::add);
        return allTeams;
    }

    /**
     * gets a single team specified by its id
     * @param id
     * @return
     */
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
    /**
     * ui for team creation
     * @return
     */
    @GetMapping("/teams/add")
    public ModelAndView showTeamCreation() {
        ModelAndView modelAndView = new ModelAndView();
        Team team = new Team();
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        modelAndView.addObject("team", team);
        modelAndView.addObject("users", users);
        modelAndView.setViewName("addteam");
        return modelAndView;
    }

    /**
     * POST chosen players and create a team with them and add the teamId to the players Team List
     * @param team
     * @param bindingResult
     * @return
     */
    @PostMapping("/teams/add")
    public ModelAndView createNewTeam(@Valid Team team, long playerAId, long playerBId, BindingResult bindingResult)
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
            /**
             *  Refactor to use an array instead of two variables
             *
             */
            team.setPlayerA(userService.findUserById(playerAId));
            team.setPlayerB(userService.findUserById(playerBId));
            teamService.addTeam(team);
            userService.addUserToTeam(team, playerAId);
            userService.addUserToTeam(team, playerBId);
            modelAndView.addObject("successMessage", "Success: Team has been added.");
            modelAndView.addObject("team", new Team());
            modelAndView.setViewName("addteam");

        }

        return modelAndView;
    }

    /**
     * deletes team identified by its id
     * @param id
     */
    @RequestMapping(method=RequestMethod.DELETE, value="/teams/delete/{id}")
    public void deleteTeam(@PathVariable long id)
    {
        teamService.deleteTeam(id);
    }

    /**
     * updates team identified by the actual object and the id
     * @param team
     * @param id
     */
    @RequestMapping(method=RequestMethod.PUT, value="/teams/update/{id}")
    public void updateTeam(@RequestBody Team team, @PathVariable long id)
    {
        teamService.updateTeam(team, id);
    }
}
