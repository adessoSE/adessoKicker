package de.adesso.adessoKicker.controllers;

import de.adesso.adessoKicker.objects.Tournament;
import de.adesso.adessoKicker.services.TeamService;
import de.adesso.adessoKicker.services.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


@Controller
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;

    @Autowired
    private TeamService teamService;

    @GetMapping("/tournaments/create")
    public ModelAndView tournamentForm() {

        ModelAndView modelAndView = new ModelAndView();
        Tournament tournament = new Tournament();
        modelAndView.addObject("tournament", tournament);
        modelAndView.setViewName("tournamentscreate");
        return modelAndView;
    }

    @PostMapping("/tournaments/create")
    public ModelAndView createNewTournament(@Valid Tournament tournament, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();
        if(bindingResult.hasErrors()) {
            modelAndView.setViewName("tournamentscreate");
        } else {
            tournamentService.saveTournament(tournament);
            modelAndView.addObject("successMessage", "Tournament has been created");
            modelAndView.addObject("tournament", new Tournament());
            modelAndView.setViewName("tournamentscreate");
        }

        return modelAndView;
    }

    //TournamentList View --> Jan
    @GetMapping("/tournaments")
    public ModelAndView showTournamentList() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("tournaments", tournamentService.getAllTournaments());
        modelAndView.setViewName("tournamentList");
        return modelAndView;
    }
    
    /*
    @GetMapping("/tournaments")
    public ModelAndView showTournamentList() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("tournaments", tournamentService.listRunningTournaments());
        modelAndView.setViewName("tournaments");
        return modelAndView;
    } */

    @GetMapping("/tournaments/{tournamentId}")
    public ModelAndView tournamentPage(@PathVariable("tournamentId") long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("tournament", tournamentService.getTournamentById(id));
        modelAndView.setViewName("tournamentspage");
        return modelAndView;
    }

    @GetMapping("tournaments/{tournamentId}/join")
    public ModelAndView showAddTeam(@PathVariable("tournamentId") long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("tournament", tournamentService.getTournamentById(id));
        modelAndView.addObject("teams", teamService.getAllTeams());
        modelAndView.setViewName("tournamentaddteam");
        return modelAndView;
    }

    @PostMapping("tournaments/{tournamentId}/join")
    public ModelAndView addTeamToTournament(@PathVariable("tournamentId") long id, @Valid Tournament tournament, BindingResult bindingResult, long teamId) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("tournament", tournamentService.getTournamentById(id));
        modelAndView.addObject("teams", teamService.getAllTeams());
        modelAndView.setViewName("tournamentaddteam");
        return modelAndView;
    }


}