package de.adesso.kicker.match;

import de.adesso.kicker.team.TeamService;
import java.util.Calendar;
import java.util.Date;
import javax.validation.Valid;

import de.adesso.kicker.user.UserService;
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
 * RestController "MatchController" that manages everything related with matches.
 *
 * @author caylak
 */

@RestController
public class MatchController {

    private MatchService matchService;
    private TeamService teamService;
    private UserService userService;
    private ModelAndView modelAndView;
    private Date time;

    @Autowired
    public MatchController(MatchService matchService, TeamService teamService, UserService userService) {

        this.matchService = matchService;
        this.teamService = teamService;
        this.userService = userService;
    }

    /**
     * getAllMatches() gets all matches that are in the database.
     * @return ModelAndView
     */
    @GetMapping("/matches")
    public ModelAndView getAllMatches() {
         modelAndView = new ModelAndView();
        if (matchService.getAllMatches().size()>0) {
            modelAndView.addObject("matches", matchService.getAllMatches());
            modelAndView.addObject("user", userService.getLoggedInUser());
        }
        else{
            modelAndView.addObject("noMatchesMessage", "Es gibt keine Matches.");
        }

        modelAndView.setViewName("match/matches");
        return modelAndView;
    }

    /**
     * getMatch() gets an unique team identified by an index.
     * @param id long
     * @return ModelAndView
     */
    @GetMapping("/matches/{id}")
    public ModelAndView getMatch(@PathVariable long id) {
        modelAndView = new ModelAndView();
        modelAndView.addObject("match", matchService.getMatchById(id));
        modelAndView.setViewName("match/page");
        return modelAndView;
    }

    /**
     * getMatchAdd() gets all relevant variables for creating a match later on.
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
     * postMatch() posts all variables written to the form and checks if these are valid. (e.g already existing data)
     * @param match          Match
     * @param bindingResult BindingResult
     * @return ModelAndView
     */

    @PostMapping("/matches/add")
    public ModelAndView postMatch(@Valid Match match, BindingResult bindingResult) {
        modelAndView = new ModelAndView();
        time = new Date();
        if (bindingResult.hasErrors()) {
        }
        try {
            if (match.getTeamA().getTeamId() != match.getTeamB().getTeamId()) {
                if (match.getDate().after(yesterday()) && (match.getTime().getHours() >= time.getHours()
                        && match.getTime().getMinutes() >= time.getMinutes())) {
                    matchService.saveMatch(match);
                    teamService.addMatchIdToTeam(match, match.getTeamA().getTeamId());
                    teamService.addMatchIdToTeam(match, match.getTeamB().getTeamId());
                    modelAndView.addObject("successMessage", "Match wurde hinzugefügt.");

                } else {
                    modelAndView.addObject("dateMessage", "Bitte kein vergangenes Datum.");
                }

            } else {
                bindingResult.rejectValue("teamA", "error.teamA");
                bindingResult.rejectValue("teamB", "error.teamB");
                modelAndView.addObject("failMessage", "Bitte keine identischen Teams auswählen.");
            }

        } catch (NullPointerException e) {
            modelAndView.addObject("dateMessage", "Bitte ein Datum + Uhrzeit.");
        }
        modelAndView.addObject("match", new Match());
        modelAndView.addObject("teams", teamService.getAllTeams());
        modelAndView.setViewName("match/create");
        return modelAndView;
    }

    /**
     * deleteMatch() deletes an unique match identified by an index.
     * @param id long
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "matches/delete/{id}")
    public void deleteMatch(@PathVariable long id) {
        matchService.deleteMatch(id);
    }

    /**
     * yesterDay() used for time comparison.
     * @return
     */
    private Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }
}
