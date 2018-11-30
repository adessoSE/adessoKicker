package de.adesso.kicker.match;

import de.adesso.kicker.notification.NotificationService;
import de.adesso.kicker.team.TeamService;
import java.util.Calendar;
import java.util.Date;
import javax.validation.Valid;

import de.adesso.kicker.user.User;
import de.adesso.kicker.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * RestController "MatchController" that manages everything related with
 * matches.
 *
 * @author caylak
 */

@RestController
public class MatchController {

    private MatchService matchService;
    private TeamService teamService;
    private UserService userService;
    private NotificationService notificationService;
    private ModelAndView modelAndView;

    @Autowired
    public MatchController(MatchService matchService, TeamService teamService, UserService userService,
            NotificationService notificationService) {

        this.matchService = matchService;
        this.teamService = teamService;
        this.userService = userService;
        this.notificationService = notificationService;
    }

    /**
     * getAllMatches() gets all matches that are in the database.
     * 
     * @return ModelAndView
     */
    @GetMapping("/matches")
    public ModelAndView getAllMatches() {
        modelAndView = new ModelAndView();
        User user = userService.getLoggedInUser();
        modelAndView.addObject("user", user);
        modelAndView.addObject("notifications", notificationService.getAllNotificationsByReceiver(user.getUserId()));
        if (matchService.getAllMatches().size() > 0) {
            modelAndView.addObject("matches", matchService.getAllMatches());
        } else {
            modelAndView.addObject("noMatchesMessage", "Es gibt keine Matches.");
        }

        modelAndView.setViewName("match/matches");
        return modelAndView;
    }

    /**
     * getMatch() gets an unique team identified by an index.
     * 
     * @param id long
     * @return ModelAndView
     */
    @GetMapping("/matches/{id}")
    public ModelAndView getMatch(@PathVariable long id) {
        modelAndView = new ModelAndView();
        User user = userService.getLoggedInUser();
        modelAndView.addObject("user", user);
        modelAndView.addObject("notifications", notificationService.getAllNotificationsByReceiver(user.getUserId()));
        modelAndView.addObject("match", matchService.getMatchById(id));
        modelAndView.setViewName("match/page");
        return modelAndView;
    }

    /**
     * getMatchAdd() gets all relevant variables for creating a match later on.
     * 
     * @return ModelAndView
     */
    @GetMapping("/matches/add")
    public ModelAndView getMatchAdd() {
        modelAndView = new ModelAndView();
        Match match = new Match();
        modelAndView.addObject("match", match);
        modelAndView.addObject("teams", teamService.getAllTeams());
        modelAndView.setViewName("match/create");
        return modelAndView;
    }

    /**
     * postMatch() posts all variables written to the form and checks if these are
     * valid. (e.g already existing data)
     * 
     * @param match         Match
     * @param bindingResult BindingResult
     * @return ModelAndView
     */

    @PostMapping("/matches/add")
    public ModelAndView postMatch(@Valid Match match, BindingResult bindingResult) {
        modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("teams", teamService.getAllTeams());
            modelAndView.setViewName("match/create");
            return modelAndView;
        }
        try {
            matchService.denyPastDate(match);
        } catch (PasteDateException p) {
            bindingResult.rejectValue("date", "error.date", "Kein vergangenes Datum.");

            modelAndView.addObject("teams", teamService.getAllTeams());
            modelAndView.setViewName("match/create");
            return modelAndView;
        }

        try {
            matchService.identicalTeams(match);
        } catch (IdenticalTeamsException i) {
            bindingResult.rejectValue("teamA", "error.teamA", "Keine identischen Teams.");
            modelAndView.addObject("teams", teamService.getAllTeams());
            modelAndView.setViewName("match/create");
            return modelAndView;
        }
        matchService.saveMatch(match);
        modelAndView.addObject("successMessage", "Match wurde hinzugefügt.");
        modelAndView.addObject("teams", teamService.getAllTeams());
        modelAndView.setViewName("match/create");
        return modelAndView;
    }
}
