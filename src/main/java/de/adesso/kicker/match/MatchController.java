package de.adesso.kicker.match;

import de.adesso.kicker.team.TeamService;
import de.adesso.kicker.user.UserService;

import java.util.Calendar;
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

@RestController
public class MatchController {

    private MatchService matchService;

    private TeamService teamService;
    
    private UserService userService;

    @Autowired
    public MatchController(MatchService matchService, TeamService teamService, UserService userService) {

        this.matchService = matchService;
        this.teamService = teamService;
        this.userService = userService;
    }

    /**
     * POST all matches on "/matches"
     *
     * @return ModelAndView
     */
    @GetMapping("/matches")
    public ModelAndView getAllMatches() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("matches", matchService.getAllMatches());
        modelAndView.addObject("user", userService.getLoggedInUser());
        modelAndView.setViewName("match/matches");
        return modelAndView;
    }

    /**
     * gets a match identified by its id
     *
     * @param id of the match
     * @return ModelAndView
     */
    @GetMapping("/matches/{id}")
    public ModelAndView getMatch(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("match", matchService.getMatchById(id));
        modelAndView.setViewName("match/page");
        return modelAndView;
    }

    /**
     * ui for adding a match
     *
     * @return ModelAndView
     */
    @GetMapping("/matches/add")
    public ModelAndView showMatchCreation() {
        ModelAndView modelAndView = new ModelAndView();
        Match match = new Match();
        modelAndView.addObject("match", match);
        modelAndView.addObject("teams", teamService.getAllTeams());
        modelAndView.setViewName("match/create");
        return modelAndView;
    }

    /**
     * POST chosen players and create a team with them and add the teamId to the
     * players Team List
     *
     * @param match         Match
     * @param bindingResult BindingResult
     * @return ModelAndView
     */

    @PostMapping("/matches/add")
    public ModelAndView createNewMatch(@Valid Match match, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Date time = new Date();
        if (bindingResult.hasErrors()) {
        }
        try {
            if (match.getTeamA().getTeamId() != match.getTeamB().getTeamId()) {
                // Time check currently not working
                if (match.getDate().after(yesterday()) && (match.getTime().getHours() >= time.getHours()
                        && match.getTime().getMinutes() >= time.getMinutes())) {
                    System.out.println(
                            "Eingegebene Zeit: " + match.getTime().getHours() + ":" + match.getTime().getMinutes());
                    System.out.println("Server Zeit: " + time.getHours() + ":" + time.getMinutes());
                    System.out.println();
                    matchService.saveMatch(match);
                    teamService.addMatchIdToTeam(match, match.getTeamA().getTeamId());
                    teamService.addMatchIdToTeam(match, match.getTeamB().getTeamId());
                    modelAndView.addObject("successMessage", "Match wurde hinzugefügt.");
                    ;
                } else {
                    System.out.println(match.getTime().getTime());
                    System.out.println(time.getTime());
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
     * deletes a match from the database identified by an id
     *
     * @param id long
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "matches/delete/{id}")
    public void deleteMatch(@PathVariable long id) {
        matchService.deleteMatch(id);
    }

    /**
     * updates an existing match by the actual object and its id
     *
     * @param match Match
     * @param id    long
     */
    @RequestMapping(method = RequestMethod.PUT, value = "matches/update/{id}")
    public void updateMatch(@RequestBody Match match, @PathVariable long id) {
        matchService.saveMatch(match);
    }

    private Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }
}
