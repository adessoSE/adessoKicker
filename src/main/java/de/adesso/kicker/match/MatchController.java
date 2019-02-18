package de.adesso.kicker.match;

import de.adesso.kicker.match.exception.PastDateException;
import de.adesso.kicker.user.User;
import de.adesso.kicker.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/matches")
public class MatchController {

    private MatchService matchService;
    private UserService userService;
    private ModelAndView modelAndView;

    @Autowired
    public MatchController(MatchService matchService, UserService userService) {

        this.matchService = matchService;
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView getAllMatches() {
        modelAndView = new ModelAndView();
        User user = userService.getLoggedInUser();
        modelAndView.addObject("user", user);
        if (matchService.getAllMatches().size() > 0) {
            modelAndView.addObject("matches", matchService.getAllMatches());
        } else {
            modelAndView.addObject("noMatchesMessage", "Es gibt keine Matches.");
        }
        modelAndView.setViewName("match/matches");
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView getMatch(@PathVariable long id) {
        modelAndView = new ModelAndView();
        User user = userService.getLoggedInUser();
        modelAndView.addObject("user", user);
        modelAndView.addObject("match", matchService.getMatchById(id));
        modelAndView.setViewName("match/page");
        return modelAndView;
    }

    @GetMapping("/add")
    public ModelAndView getMatchAdd() {
        modelAndView = new ModelAndView();
        Match match = new Match();
        modelAndView.addObject("match", match);
        modelAndView.setViewName("match/create");
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView postMatch(@Valid Match match, BindingResult bindingResult) {
        modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("match/create");
            return modelAndView;
        }
        try {
            matchService.denyPastDate(match);
        } catch (PastDateException p) {
            bindingResult.rejectValue("date", "error.date", "Kein vergangenes Datum.");

            modelAndView.setViewName("match/create");
            return modelAndView;
        }

        modelAndView.addObject("successMessage", "Match wurde hinzugefügt.");
        modelAndView.setViewName("match/create");
        return modelAndView;
    }
}
