package de.adesso.kicker.tournament.lastmanstanding;

import de.adesso.kicker.tournament.Tournament;
import de.adesso.kicker.tournament.TournamentController;
import de.adesso.kicker.tournament.TournamentFormats;
import de.adesso.kicker.tournament.singleelimination.SingleElimination;
import de.adesso.kicker.user.User;
import de.adesso.kicker.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class LastManStandingController {

    private LastManStandingService lastManStandingService;
    private UserService userService;

    @Autowired
    public LastManStandingController(LastManStandingService lastManStandingService, UserService userService) {

        this.lastManStandingService = lastManStandingService;
        this.userService = userService;
    }

    @GetMapping("/tournaments/create/lastmanstanding")
    public ModelAndView lastManStandingForm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("tournament", new LastManStanding());
        modelAndView.setViewName("tournament/create");
        return modelAndView;
    }

    @PostMapping("/tournaments/create/lastmanstanding")
    public ModelAndView createLastManStanding(@Valid LastManStanding lastManStanding, BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {

            modelAndView.addObject("tournament", new LastManStanding());
            modelAndView.setViewName("tournament/create");
            return modelAndView;
        } else {
            System.out.println(lastManStanding);
            lastManStandingService.saveTournament(lastManStanding);
            redirectAttributes.addFlashAttribute("successMessage", "Tournament has been created");
            redirectAttributes.addFlashAttribute("tournamentFormats", TournamentFormats.values());
            modelAndView.setViewName("redirect:/tournaments/create");
        }
        return modelAndView;
    }

    public ModelAndView getLastManStandingPage(Tournament tournament) {
        ModelAndView modelAndView = new ModelAndView();
        LastManStanding lastManStanding = (LastManStanding) tournament;
        modelAndView.addObject("tournament", lastManStanding);
        modelAndView.setViewName("tournament/page");
        return modelAndView;
    }

    public ModelAndView joinTournament(Tournament tournament) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("tournament", tournament);
        modelAndView.addObject("users", userService.getAllUsers());
        modelAndView.setViewName("tournament/addplayer");
        return modelAndView;
    }

    public ModelAndView addPlayerToTournament(Tournament tournament, User user) {
        ModelAndView modelAndView = new ModelAndView();
        lastManStandingService.addPlayer(tournament, user);
        modelAndView.addObject("tournament", tournament);
        modelAndView.addObject("users", userService.getAllUsers());
        modelAndView.addObject("successMessage", "Player added to tournament");
        modelAndView.setViewName("tournament/addplayer");
        return modelAndView;
    }

    public ModelAndView showLivesMap(Tournament tournament) {
        LastManStanding lastManStanding = (LastManStanding) tournament;
        ModelAndView modelAndView = new ModelAndView();
        lastManStandingService.createLivesMap(lastManStanding);
        modelAndView.addObject("tournament", lastManStanding);
        modelAndView.setViewName("tournament/maptest");
        return modelAndView;
    }

    @GetMapping(value = "/tournaments/create", params = { "LASTMANSTANDING" })
    public ModelAndView tournamentCreation() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("tournament", new LastManStanding());
        modelAndView.setViewName("tournament/createlastmanstanding");
        return modelAndView;
    }

    @PostMapping(value = "/tournaments/create", params = { "LASTMANSTANDING" })
    public ModelAndView createSingleElimination(@Valid SingleElimination singleElimination, BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {

            modelAndView.addObject("tournament", new LastManStanding());
            modelAndView.setViewName("tournament/createsingleelimination");
        } else {

            lastManStandingService.saveTournament(singleElimination);
            redirectAttributes.addFlashAttribute("successMessage", "Tournament has been created");
            redirectAttributes.addFlashAttribute("tournamentFormats", TournamentFormats.values());
            modelAndView.setViewName("redirect:/tournaments/create");
        }

        return modelAndView;
    }
}
