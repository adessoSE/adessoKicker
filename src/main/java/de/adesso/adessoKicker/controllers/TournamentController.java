package de.adesso.adessoKicker.controllers;

import de.adesso.adessoKicker.objects.Team;
import de.adesso.adessoKicker.objects.Tournament;
import de.adesso.adessoKicker.services.TeamService;
import de.adesso.adessoKicker.services.TournamentService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TournamentController {

    private TournamentService tournamentService;
    private TeamService teamService;

    @Autowired
    public TournamentController(TournamentService tournamentService, TeamService teamService) {

        this.tournamentService = tournamentService;
        this.teamService = teamService;
    }

    @GetMapping("/tournaments/create")
    public ModelAndView tournamentForm() {

        ModelAndView modelAndView = new ModelAndView();
        Tournament tournament = new Tournament();
        modelAndView.addObject("tournament", tournament);
        modelAndView.setViewName("tournament/create");
        return modelAndView;
    }

    @PostMapping("/tournaments/create")
    public ModelAndView createNewTournament(@Valid Tournament tournament, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("tournament/create");
        } else {
            tournamentService.saveTournament(tournament);
            modelAndView.addObject("successMessage", "Tournament has been created");
            modelAndView.addObject("tournament", new Tournament());
            modelAndView.setViewName("tournament/create");
        }

        return modelAndView;
    }

    // TournamentList View --> Jan
    @GetMapping("/tournaments")
    public ModelAndView showTournamentList() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("tournaments", tournamentService.getAllTournaments());
        modelAndView.setViewName("tournament/list");
        return modelAndView;
    }

    @GetMapping("/tournaments/{tournamentId}")
    public ModelAndView tournamentPage(@PathVariable("tournamentId") long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("tournament", tournamentService.getTournamentById(id));
        modelAndView.setViewName("tournament/page");
        return modelAndView;
    }

    @GetMapping("tournaments/{tournamentId}/join")
    public ModelAndView showAddTeam(@PathVariable("tournamentId") long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("tournament", tournamentService.getTournamentById(id));
        modelAndView.addObject("teams", teamService.getAllTeams());
        modelAndView.setViewName("tournament/addteam");
        return modelAndView;
    }

    @PostMapping("tournaments/{tournamentId}/join")
    public ModelAndView addTeamToTournament(@PathVariable("tournamentId") long id, long teamId) {

        Tournament tournament = tournamentService.getTournamentById(id);
        Team team = teamService.getTeamById(teamId);
        ModelAndView modelAndView = new ModelAndView();

        if (tournament.getTeams().contains(teamService.getTeamById(teamId))) {
            modelAndView.addObject("failMessage", "Team already is in tournament");
            modelAndView.addObject("tournament", tournament);
            modelAndView.addObject("teams", teamService.getAllTeams());
            modelAndView.setViewName("tournament/addteam");
            return modelAndView;
        }

        if (tournament.getPlayers().contains(team.getPlayerA())
                || tournament.getPlayers().contains(team.getPlayerB())) {

            modelAndView.addObject("failMessage", "Player already is in tournament");
            modelAndView.addObject("tournament", tournament);
            modelAndView.addObject("teams", teamService.getAllTeams());
            modelAndView.setViewName("tournament/addteam");
            return modelAndView;
        }

        tournamentService.addTeamToTournament(tournamentService.getTournamentById(id), team);
        tournamentService.addPlayers(tournament, team.getPlayerA());
        tournamentService.addPlayers(tournament, team.getPlayerB());
        modelAndView.addObject("tournament", tournament);
        modelAndView.addObject("teams", teamService.getAllTeams());
        modelAndView.setViewName("tournament/addteam");
        return modelAndView;
    }

    @GetMapping("tournaments/{tournamentId}/treetest")
    public ModelAndView showTournamentTree(@PathVariable("tournamentId") long id) {

        ModelAndView modelAndView = new ModelAndView();
        List<Team> teams = tournamentService.getTournamentById(id).getTeams();
        tournamentService.createTournamentTree(teams, tournamentService.getTournamentById(id));
        modelAndView.addObject("tournament", tournamentService.getTournamentById(id));
        modelAndView.setViewName("tournament/treetest");
        return modelAndView;
    }
}
