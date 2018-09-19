package de.adesso.adessoKicker.controllers;

import de.adesso.adessoKicker.objects.Tournament;
import de.adesso.adessoKicker.services.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import javax.validation.Valid;


@Controller
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;

    @GetMapping("/tournaments/create")
    public ModelAndView tournamentForm() {

        ModelAndView modelAndView = new ModelAndView();
        Tournament tournament = new Tournament();
        modelAndView.addObject("tournament", tournament);
        modelAndView.setViewName("createTournament");
        return modelAndView;
    }

    @PostMapping("/tournaments/create")
    public ModelAndView createNewTournament(@Valid Tournament tournament, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();
        if(bindingResult.hasErrors()) {
            modelAndView.setViewName("createTournament");
        } else {
            tournamentService.saveTournament(tournament);
            modelAndView.addObject("successMessage", "Tournament has been created");
            modelAndView.addObject("tournament", new Tournament());
            modelAndView.setViewName("createTournament");
        }

        return modelAndView;
    }

}
