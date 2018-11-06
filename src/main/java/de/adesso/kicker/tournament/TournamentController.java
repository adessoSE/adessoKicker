package de.adesso.kicker.tournament;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TournamentController {

    private TournamentService tournamentService;

    @Autowired
    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @GetMapping(value = "/tournaments/create")
    public ModelAndView chooseFormat() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("tournamentFormats", TournamentFormats.values());
        modelAndView.setViewName("tournament/create");
        return modelAndView;
    }

    @GetMapping("/tournaments")
    public ModelAndView showTournamentList() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("tournaments", tournamentService.getAllTournaments());
        modelAndView.setViewName("tournament/list");
        return modelAndView;
    }

    @GetMapping("/tournaments/{tournamentId}")
    public ModelAndView getTournamentPage(@PathVariable("tournamentId") long id) {
        Tournament tournament = tournamentService.getTournamentById(id);
        return tournamentService.getPage(tournament);
    }

    @GetMapping("tournaments/{tournamentId}/join")
    public ModelAndView getJoinTournament(@PathVariable("tournamentId") long id) {
        Tournament tournament = tournamentService.getTournamentById(id);
        return tournamentService.getJoinTournament(tournament);
    }

    @PostMapping("tournaments/{tournamentId}/join")
    public ModelAndView postJoinTournament(@PathVariable("tournamentId") long tournamentId, long id) {
        Tournament tournament = tournamentService.getTournamentById(tournamentId);
        return tournamentService.postJoinTournament(tournament, id);
    }

    @GetMapping("tournaments/{tournamentId}/bracket")
    public ModelAndView getBracket(@PathVariable("tournamentId") long tournamentId) {
        Tournament tournament = tournamentService.getTournamentById(tournamentId);
        return tournamentService.getBracket(tournament);
    }
}
