package de.adesso.kicker.match;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import de.adesso.kicker.match.exception.InvalidCreatorException;
import de.adesso.kicker.match.exception.MatchNotFoundException;
import de.adesso.kicker.match.exception.SamePlayerException;
import de.adesso.kicker.user.User;
import de.adesso.kicker.user.UserService;

@Controller
@RequestMapping("/matches")
public class MatchController {

    private MatchService matchService;
    private UserService userService;

    @Autowired
    public MatchController(MatchService matchService, UserService userService) {
        this.matchService = matchService;
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView getAllMatches() {
        var modelAndView = new ModelAndView();
        User user = userService.getLoggedInUser();
        modelAndView.addObject("user", user);
        modelAndView.addObject("matches", matchService.getAllMatches());
        modelAndView.setViewName("match/matches.html");
        return modelAndView;
    }

    @GetMapping("/m/{id}")
    public ModelAndView getMatch(@PathVariable String id) {
        var modelAndView = new ModelAndView();
        Match match;
        try {
            match = matchService.getMatchById(id);
        } catch (MatchNotFoundException e) {
            modelAndView.setStatus(HttpStatus.NOT_FOUND);
            return modelAndView;
        }
        User user = userService.getLoggedInUser();
        modelAndView.addObject("user", user);
        modelAndView.addObject("match", match);
        modelAndView.setViewName("match/page.html");
        return modelAndView;
    }

    @GetMapping("/add")
    public ModelAndView getAddMatch() {
        var modelAndView = new ModelAndView();
        return addMatchView(modelAndView);
    }

    @PostMapping("/add")
    public ModelAndView postAddMatch(@Valid Match match, BindingResult bindingResult) {
        var modelAndView = new ModelAndView();
        System.out.println(bindingResult.getModel());
        if (bindingResult.hasErrors()) {
            if (bindingResult.hasFieldErrors("date")) {
                modelAndView.addObject("noDate", true);
            }
            if (bindingResult.hasFieldErrors("teamAPlayer1") || bindingResult.hasFieldErrors("teamBPlayer1")) {
                System.out.println(bindingResult);
                modelAndView.addObject("nullPlayer", true);
            }
            if (bindingResult.hasFieldErrors("winnerTeamA")) {
                modelAndView.addObject("noWinner", true);
            }
        } else {
            try {
                matchService.addMatchEntry(match);
                modelAndView.addObject("successMessage", true);
            } catch (InvalidCreatorException e) {
                modelAndView.addObject("invalidCreator", true);
            } catch (SamePlayerException e) {
                modelAndView.addObject("samePlayer", true);
            }
        }
        return addMatchView(modelAndView);
    }

    private ModelAndView addMatchView(ModelAndView modelAndView) {
        modelAndView.addObject("match", new Match());
        modelAndView.addObject("users", userService.getAllUsers());
        modelAndView.setViewName("match/add.html");
        return modelAndView;
    }
}
