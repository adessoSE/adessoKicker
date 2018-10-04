package de.adesso.kicker.team;

import de.adesso.kicker.user.UserService;
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

/**
 * Controller managing Teams
 *
 * @author caylak
 */
@RestController
public class TeamController {

    private TeamService teamService;
    private UserService userService;

    @Autowired
    public TeamController(TeamService teamService, UserService userService) {

        this.teamService = teamService;
        this.userService = userService;
    }

    /**
     * gets all teams
     *
     * @return ModelAndView
     */
    @GetMapping("/teams")
    public ModelAndView getAllTeams() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("teams", teamService.getAllTeams());
        modelAndView.setViewName("team/teams");
        return modelAndView;
    }

    /**
     * gets a single team specified by its id
     *
     * @param id long
     * @return ModelAndView
     */
    @GetMapping("/teams/{teamId}")
    public ModelAndView showTeamPage(@PathVariable("teamId") long id) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(teamService.getTeamById(id));
        modelAndView.setViewName("team/page");
        return modelAndView;
    }

    /**
     * ui for team creation
     *
     * @return ModelAndView
     */
    @GetMapping("/teams/add")
    public ModelAndView showTeamCreation() {
        ModelAndView modelAndView = new ModelAndView();
        Team team = new Team();
        modelAndView.addObject("team", team);
        modelAndView.addObject("users", userService.getAllUsers());
        modelAndView.setViewName("team/add");
        return modelAndView;
    }

    /**
     * POST chosen players and create a team with them and add the teamId to the
     * players Team List
     *
     * @param team          Team
     * @param bindingResult BindingResult
     * @return ModelAndView
     */
    @PostMapping("/teams/add")
    public ModelAndView createNewTeam(@Valid Team team, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Team teamExists = teamService.findByTeamName(team.getTeamName());
        if (teamExists != null) {
            bindingResult.rejectValue("teamName", "error.teamName",
                    "Teamname existiert bereits. Bitte einen anderen wählen.");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("team", team);
            modelAndView.addObject("users", userService.getAllUsers());
            modelAndView.setViewName("team/add");
        } else {
            if (team.getPlayerA().getUserId() != team.getPlayerB().getUserId()) {
                teamService.saveTeam(team);
                // userService.addTeamIdToUser(team, team.getPlayerA().getUserId());
                // serService.addTeamIdToUser(team, team.getPlayerB().getUserId());
                modelAndView.addObject("successMessage", "Team wurde hinzugefügt.");
                modelAndView.addObject("team", new Team());
                modelAndView.addObject("users", userService.getAllUsers());
                modelAndView.setViewName("team/add");
            } else {
                bindingResult.rejectValue("playerA", "error.playerA");
                bindingResult.rejectValue("playerB", "error.playerB");
                modelAndView.addObject("team", team);
                modelAndView.addObject("users", userService.getAllUsers());
                modelAndView.addObject("failMessage", "Bitte keine identischen Spieler auswählen.");
                modelAndView.setViewName("team/add");
            }
        }

        return modelAndView;
    }

    /**
     * deletes team identified by its id
     *
     * @param id long
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/teams/delete/{id}")
    public void deleteTeam(@PathVariable long id) {
        teamService.deleteTeam(id);
    }

    /**
     * updates team identified by the actual object and the id
     *
     * @param team Team
     * @param id   long
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/teams/update/{id}")
    public void updateTeam(@RequestBody Team team, @PathVariable long id) {
        teamService.saveTeam(team);
    }
}
