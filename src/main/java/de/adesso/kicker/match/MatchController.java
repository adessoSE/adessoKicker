package de.adesso.kicker.match;

import de.adesso.kicker.user.User;
import de.adesso.kicker.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MatchController {

    private MatchService matchService;
    private UserService userService;

    @Autowired
    public MatchController(MatchService matchService, UserService userService) {
        this.matchService = matchService;
        this.userService = userService;
    }

    @GetMapping("/matches")
    public ModelAndView getAllMatches() {
        var modelAndView = new ModelAndView();
        User user = userService.getLoggedInUser();
        modelAndView.addObject("user", user);
        modelAndView.addObject("matches", matchService.getAllMatches());
        modelAndView.setViewName("match/matches.html");
        return modelAndView;
    }

    @GetMapping("/matches/m/{id}")
    public ModelAndView getMatch(@PathVariable long id) {
        var modelAndView = new ModelAndView();
        User user = userService.getLoggedInUser();
        modelAndView.addObject("user", user);
        modelAndView.addObject("match", matchService.getMatchById(id));
        modelAndView.setViewName("match/page.html");
        return modelAndView;
    }

    @GetMapping("/matches/create")
    public ModelAndView getCreateMatch() {
        var modelAndView = new ModelAndView();
        modelAndView.addObject("match", new Match());
        modelAndView.addObject("users", userService.getAllUsers());
        modelAndView.addObject("currentUser", userService.getLoggedInUser());
        modelAndView.setViewName("match/create.html");
        return modelAndView;
    }

    @PostMapping("/matches/create")
    public ModelAndView postCreateMatch(Match match) {
        if (match.getTeamAPlayer1() == null || match.getTeamBPlayer1() == null) {
            // TODO: Proper Exception and handling
            throw new NullPointerException();
        }
        if (match.getDate() == null) {
            // TODO: Proper Exception and handling
            throw new NullPointerException();
        }

        matchService.addMatchEntry(match);
        var modelAndView = new ModelAndView();
        modelAndView.addObject("match", new Match());
        modelAndView.addObject("users", userService.getAllUsers());
        modelAndView.addObject("currentUser", userService.getLoggedInUser());
        modelAndView.setViewName("match/create.html");
        return modelAndView;
    }
}
