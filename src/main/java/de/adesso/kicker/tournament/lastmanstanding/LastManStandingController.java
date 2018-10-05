package de.adesso.kicker.tournament.lastmanstanding;

import de.adesso.kicker.tournament.TournamentFormats;
import de.adesso.kicker.tournament.TournamentService;
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

    private TournamentService tournamentService;

    public LastManStandingController(TournamentService tournamentService) {

        this.tournamentService = tournamentService;
    }
    @GetMapping("/tournaments/create/lastmanstanding")
    public ModelAndView lastManStandingForm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("lastManStanding", new LastManStanding());
        modelAndView.setViewName("tournament/createlastmanstanding");
        return modelAndView;
    }

    @PostMapping("/tournaments/create/lastmanstanding")
    public ModelAndView createLastManStanding (@Valid LastManStanding lastManStanding, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {

            modelAndView.addObject("lastManStanding", new LastManStanding());
            modelAndView.setViewName("tournament/createlastmanstanding");
            return modelAndView;
        } else {
            System.out.println(lastManStanding);
            tournamentService.saveTournament(lastManStanding);
            redirectAttributes.addFlashAttribute("successMessage", "Tournament has been created");
            redirectAttributes.addFlashAttribute("tournamentFormats", TournamentFormats.values());
            modelAndView.setViewName("redirect:/tournaments/create");
        }
        return modelAndView;
    }
}
