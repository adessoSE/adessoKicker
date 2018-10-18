package de.adesso.kicker.team;

import de.adesso.kicker.user.UserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * RestController "TeamController" that manages everything related with teams.
 *
 * @author caylak
 */
@RestController
public class TeamController {

    private TeamService teamService;
    private UserService userService;
    private ModelAndView modelAndView;
    @Autowired
    public TeamController(TeamService teamService, UserService userService) {

        this.teamService = teamService;
        this.userService = userService;
    }

    /**
     * getAllTeams() gets all teams that are in the database.
     * @return ModelAndView
     */
    @GetMapping("/teams")
    public ModelAndView getAllTeams() {
        modelAndView = new ModelAndView();
        modelAndView.addObject("teams", teamService.getAllTeams());
        modelAndView.setViewName("team/teams");
        return modelAndView;
    }

    /**
     * getTeam() gets an unique team identified by an index.
     * @param id long
     * @return ModelAndView
     */
    @GetMapping("/teams/{teamId}")
    public ModelAndView getTeam(@PathVariable("teamId") long id) {

        modelAndView = new ModelAndView();
        modelAndView.addObject(teamService.getTeamById(id));
        modelAndView.setViewName("team/page");
        return modelAndView;
    }

    /**
     * getTeamAdd() gets all relevant variables for creating a team later on.
     * @return ModelAndView
     */
    @GetMapping("/teams/add")
    public ModelAndView getTeamAdded() {
        modelAndView = new ModelAndView();
        Team team = new Team();
        modelAndView.addObject("team", team);
        modelAndView.addObject("users", userService.getAllUsers());
        modelAndView.setViewName("team/add");
        return modelAndView;
    }

    /**
     * postTeam() posts all variables written to the form and checks if these are valid. (e.g already existing data)
     * @param team          Team
     * @param bindingResult BindingResult
     * @return ModelAndView
     */
    @PostMapping("/teams/add")
    public ModelAndView postTeam(@Valid Team team, BindingResult bindingResult) {
        modelAndView = new ModelAndView();
        Team teamExists = teamService.findByTeamName(team.getTeamName());
        if (teamExists != null) {
            bindingResult.rejectValue("teamName", "error.teamName",
                    "Teamname existiert bereits. Bitte einen anderen wählen.");
        }
        if (bindingResult.hasErrors()) {
        } else {
            if (team.getPlayerA().getUserId() != team.getPlayerB().getUserId()) {
                teamService.saveTeam(team);
                modelAndView.addObject("successMessage", "Team wurde hinzugefügt.");
            } else {
                bindingResult.rejectValue("playerA", "error.playerA");
                bindingResult.rejectValue("playerB", "error.playerB");
                modelAndView.addObject("failMessage", "Bitte keine identischen Spieler auswählen.");
            }
        }
        modelAndView.addObject("team", team);
        modelAndView.addObject("users", userService.getAllUsers());
        modelAndView.setViewName("team/add");
        return modelAndView;
    }

    /**
     * deleteTeam() deletes an unique team identified by an index.
     * @param id long
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/teams/delete/{id}")
    public void deleteTeam(@PathVariable long id) {
        teamService.deleteTeamById(id);
    }

    /**
     * getTeamsSearchbar() gets all teams from the input of the user.
     * @param teamName
     * @return
     */
    @GetMapping(value = "teams/list")
    public ModelAndView getTeamsSearchbar(@RequestParam(value = "search", required = false) String teamName)
                                         {
        modelAndView = new ModelAndView();
        try {
            modelAndView.addObject("search", teamService.getTeamByName(teamName));
        } catch (Exception i) {
        }
        modelAndView.setViewName("user/testteamsearch");
        return modelAndView;
    }
}
