package de.adesso.kicker.match.controller;

import de.adesso.kicker.match.exception.FutureDateException;
import de.adesso.kicker.match.exception.InvalidCreatorException;
import de.adesso.kicker.match.exception.SamePlayerException;
import de.adesso.kicker.match.persistence.Match;
import de.adesso.kicker.match.service.MatchService;
import de.adesso.kicker.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/matches")
public class MatchController {

    private final MatchService matchService;

    private final UserService userService;

    @GetMapping("/add")
    public ModelAndView getAddMatch() {
        return addMatchView(new ModelAndView());
    }

    @PostMapping("/add")
    public ModelAndView postAddMatch(@Valid Match match, BindingResult bindingResult) {
        var modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            if (bindingResult.hasFieldErrors("date")) {
                modelAndView.addObject("noDate", true);
            }
            if (bindingResult.hasFieldErrors("teamAPlayer1") || bindingResult.hasFieldErrors("teamBPlayer1")) {
                modelAndView.addObject("nullPlayer", true);
            }
            if (bindingResult.hasFieldErrors("winnerTeamA")) {
                modelAndView.addObject("noWinner", true);
            }
            return addMatchView(modelAndView);
        }

        try {
            matchService.addMatchEntry(match);
            modelAndView.addObject("successMessage", true);
        } catch (FutureDateException e) {
            modelAndView.addObject("futureDate", true);
        } catch (InvalidCreatorException e) {
            modelAndView.addObject("invalidCreator", true);
        } catch (SamePlayerException e) {
            modelAndView.addObject("samePlayer", true);
        }
        return addMatchView(modelAndView);
    }

    private ModelAndView addMatchView(ModelAndView modelAndView) {
        modelAndView.addObject("match", new Match());
        modelAndView.addObject("users", userService.getAllUsers());
        modelAndView.addObject("currentUser", userService.getLoggedInUser());
        modelAndView.setViewName("sites/matchresult.html");
        return modelAndView;
    }
}
