package de.adesso.kicker.match;

import de.adesso.kicker.match.exception.*;
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
    public ModelAndView getMatch(@PathVariable String id) {
        var modelAndView = new ModelAndView();
        Match match;
        try {
            match = matchService.getMatchById(id);
        } catch (MatchNotFoundException e) {
            modelAndView.setViewName("error/404.html");
            return modelAndView;
        }
        User user = userService.getLoggedInUser();
        modelAndView.addObject("user", user);
        modelAndView.addObject("match", match);
        modelAndView.setViewName("match/page.html");
        return modelAndView;
    }

    @GetMapping("/matches/add")
    public ModelAndView getAddMatch() {
        var modelAndView = new ModelAndView();
        return addMatchView(modelAndView);
    }

    @PostMapping("/matches/add")
    public ModelAndView postAddMatch(Match match) {
        var modelAndView = new ModelAndView();
        try {
            checkMatch(match);
            try {
                matchService.addMatchEntry(match);
                modelAndView.addObject("successMessage", true);
            } catch (InvalidCreatorException e) {
                modelAndView.addObject("invalidCreator", true);
            } catch (SamePlayerException e) {
                modelAndView.addObject("samePlayer", true);
            }
        } catch (NoDateException e) {
            modelAndView.addObject("noDate", true);
        } catch (NullPlayersException e) {
            modelAndView.addObject("nullPlayer", true);
        }
        return addMatchView(modelAndView);
    }

    private ModelAndView addMatchView(ModelAndView modelAndView) {
        modelAndView.addObject("match", new Match());
        modelAndView.addObject("users", userService.getAllUsers());
        modelAndView.addObject("currentUser", userService.getLoggedInUser());
        modelAndView.setViewName("match/add.html");
        return modelAndView;
    }

    private void checkMatch(Match match) {
        if (match.getDate() == null) {
            throw new NoDateException();
        }
        if (match.getTeamAPlayer1() == null || match.getTeamBPlayer1() == null) {
            throw new NullPlayersException();
        }
    }
}
