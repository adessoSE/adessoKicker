package de.adesso.kicker.match.controller;

import de.adesso.kicker.match.exception.FutureDateException;
import de.adesso.kicker.match.exception.InvalidCreatorException;
import de.adesso.kicker.match.exception.SamePlayerException;
import de.adesso.kicker.match.persistence.Match;
import de.adesso.kicker.match.service.MatchService;
import de.adesso.kicker.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/matches")
public class MatchController {

    private final MatchService matchService;

    private final UserService userService;

    @GetMapping("/add")
    public String getAddMatch(Model model) {
        defaultAddMatchModel(model);
        return "sites/matchresult.html";
    }

    @PostMapping("/add")
    public String postAddMatch(@Valid Match match, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            if (bindingResult.hasFieldErrors("date")) {
                model.addAttribute("noDate", true);
            }
            if (bindingResult.hasFieldErrors("teamAPlayer1") || bindingResult.hasFieldErrors("teamBPlayer1")) {
                model.addAttribute("nullPlayer", true);
            }
            if (bindingResult.hasFieldErrors("winnerTeamA")) {
                model.addAttribute("noWinner", true);
            }
            defaultAddMatchModel(model);
            return "sites/matchresult.html";
        }

        try {
            matchService.addMatchEntry(match);
            model.addAttribute("successMessage", true);
        } catch (FutureDateException e) {
            model.addAttribute("futureDate", true);
        } catch (InvalidCreatorException e) {
            model.addAttribute("invalidCreator", true);
        } catch (SamePlayerException e) {
            model.addAttribute("samePlayer", true);
        } catch (MailException e) {
            model.addAttribute("tooManyMails", true);
        }
        defaultAddMatchModel(model);
        return "sites/matchresult.html";
    }

    private void defaultAddMatchModel(Model model) {
        var match = new Match();
        var user = userService.getLoggedInUser();
        var users = userService.getAllUsers();
        model.addAttribute("match", match);
        model.addAttribute("currentUser", user);
        model.addAttribute("users", users);
    }
}
