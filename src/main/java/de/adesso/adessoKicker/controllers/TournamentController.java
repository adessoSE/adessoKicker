package de.adesso.adessoKicker.controllers;

import de.adesso.adessoKicker.objects.Tournament;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

public class TournamentController {

    @GetMapping("/tournaments/create")
    public ModelAndView tournamentForm() {

        ModelAndView modelAndView = new ModelAndView();
        Tournament tournament = new Tournament();
        modelAndView.addObject("tournament", tournament);
        modelAndView.setViewName("tournamentform");
        return modelAndView;
    }

    @PostMapping("/tournaments/create")
    public ModelAndView createNewTournament(@Valid Tournament tournament, BindingResult bindingResult) {

    }

}
