package de.adesso.kicker.team;

import de.adesso.kicker.notification.NotificationService;
import de.adesso.kicker.notification.teamjoinrequest.TeamJoinRequestService;
import de.adesso.kicker.user.User;
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
    private TeamJoinRequestService teamJoinRequestService;
    private UserService userService;
    private NotificationService notificationService;
    private ModelAndView modelAndView;

    @Autowired
    public TeamController(TeamService teamService, UserService userService, NotificationService notificationService, TeamJoinRequestService teamJoinRequestService) {

        this.teamService = teamService;
        this.teamJoinRequestService = teamJoinRequestService;
        this.userService = userService;
        this.notificationService = notificationService;
    }

    /**
     * getAllTeams() gets all teams that are in the database.
     * 
     * @return ModelAndView
     */
    @GetMapping("/teams")
    public ModelAndView getAllTeams() {
        modelAndView = new ModelAndView();
        User user = userService.getLoggedInUser();
        modelAndView.addObject("user", user);
        modelAndView.addObject("notifications", notificationService.getAllNotificationsByReceiver(user.getUserId()));
        modelAndView.addObject("teams", teamService.getAllTeams());
        modelAndView.setViewName("team/teams");
        return modelAndView;
    }

    /**
     * getTeam() gets an unique team identified by an index.
     * 
     * @param id long
     * @return ModelAndView
     */
    @GetMapping("/teams/{teamId}")
    public ModelAndView getTeam(@PathVariable("teamId") long id) {

        modelAndView = new ModelAndView();
        User user = userService.getLoggedInUser();
        modelAndView.addObject("user", user);
        modelAndView.addObject("notifications", notificationService.getAllNotificationsByReceiver(user.getUserId()));
        modelAndView.addObject(teamService.getTeamById(id));
        modelAndView.setViewName("team/page");
        return modelAndView;
    }

    /**
     * getTeamAdd() gets all relevant variables for creating a team later on.
     * 
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
     * postTeam() posts all variables written to the form and checks if these are
     * valid. (e.g already existing data)
     * 
     * @param team          Team
     * @param bindingResult BindingResult
     * @return ModelAndView
     */

    // Validation in Services
    @PostMapping("/teams/add")
    public ModelAndView postTeam(@Valid Team team, BindingResult bindingResult) {
        modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("users", userService.getAllUsers());
            modelAndView.setViewName("team/add");
            return modelAndView;
        }
        try {
            teamService.denySameTeamPlayers(team);
        } catch (IdenticalPlayersException e) {
            bindingResult.rejectValue("playerA", "error.playerA", "Keine identischen Spieler.");
            bindingResult.rejectValue("playerB", "error.playerB", "Keine identischen Spieler.");
            modelAndView.addObject("users", userService.getAllUsers());
            modelAndView.setViewName("team/add");
            return modelAndView;
        }
        try {
            teamService.denySameTeam(team);
        } catch (TeamNameExistingException t) {
            bindingResult.rejectValue("teamName", "error.teamName", "Teamname existiert bereits.");
            modelAndView.addObject("users", userService.getAllUsers());
            modelAndView.setViewName("team/add");
            return modelAndView;
        }
        teamJoinRequestService.saveTeamJoinRequest(team.getTeamName(), team.getPlayerB().getUserId(), team.getPlayerA().getUserId());
        modelAndView.addObject("successMessage", "Team wurde erfolgreich erstellt.");
        modelAndView.addObject("users", userService.getAllUsers());
        modelAndView.setViewName("team/add");
        return modelAndView;
    }
}
