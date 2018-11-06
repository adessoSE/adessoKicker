package de.adesso.kicker.tournament.lastmanstanding;

import de.adesso.kicker.tournament.TournamentControllerInterface;
import de.adesso.kicker.tournament.Tournament;
import de.adesso.kicker.tournament.TournamentFormats;
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
public class LastManStandingController implements TournamentControllerInterface<LastManStanding> {

    private LastManStandingService lastManStandingService;
    private UserService userService;

    @Autowired
    public LastManStandingController(LastManStandingService lastManStandingService, UserService userService) {

        this.lastManStandingService = lastManStandingService;
        this.userService = userService;
    }

    @Override
    public Class<LastManStanding> appliesTo() {
        return LastManStanding.class;
    }

    @Override
    public ModelAndView getPage(LastManStanding lastManStanding) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("tournament", lastManStanding);
        modelAndView.setViewName("tournament/page");
        return modelAndView;
    }

    @Override
    public ModelAndView getJoinTournament(LastManStanding tournament) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("tournament", tournament);
        modelAndView.addObject("users", userService.getAllUsers());
        modelAndView.setViewName("tournament/addplayer");
        return modelAndView;
    }

    @Override
    public ModelAndView postJoinTournament(LastManStanding tournament, long id) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.getUserById(id);
        modelAndView.addObject("tournament", tournament);
        modelAndView.addObject("users", userService.getAllUsers());
        modelAndView.setViewName("tournament/addplayer");
        try {
            lastManStandingService.checkPlayerInTournament(tournament, user);
        } catch (PlayerAlreadyInTournamentException e) {
            modelAndView.addObject("failMessage", "Player already in tournament");
            return modelAndView;
        }
        lastManStandingService.addPlayer(tournament, user);
        modelAndView.addObject("successMessage", "Player added to tournament");
        return modelAndView;
    }

    @Override
    public ModelAndView getBracket(LastManStanding tournament) {
        ModelAndView modelAndView = new ModelAndView();
        lastManStandingService.createLivesMap(tournament);
        modelAndView.addObject("tournament", tournament);
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
    public ModelAndView createLastManStanding(@Valid LastManStanding lastManStanding, BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {

            modelAndView.addObject("tournament", new LastManStanding());
            modelAndView.setViewName("tournament/createlastmanstanding");
        } else {

            lastManStandingService.saveTournament(lastManStanding);
            redirectAttributes.addFlashAttribute("successMessage", "Tournament has been created");
            redirectAttributes.addFlashAttribute("tournamentFormats", TournamentFormats.values());
            modelAndView.setViewName("redirect:/tournaments/create");
        }

        return modelAndView;
    }
}
