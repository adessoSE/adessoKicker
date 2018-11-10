package de.adesso.kicker.tournament.singleelimination;

import de.adesso.kicker.team.Team;
import de.adesso.kicker.team.TeamService;
import de.adesso.kicker.tournament.TournamentControllerInterface;
import de.adesso.kicker.tournament.TournamentFormats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class SingleEliminationController implements TournamentControllerInterface<SingleElimination> {

    private SingleEliminationService singleEliminationService;
    private TeamService teamService;

    @Autowired
    public SingleEliminationController(SingleEliminationService singleEliminationService, TeamService teamService) {

        this.singleEliminationService = singleEliminationService;
        this.teamService = teamService;
    }

    @Override
    public Class<SingleElimination> appliesTo() {
        return SingleElimination.class;
    }

    @Override
    public ModelAndView getPage(SingleElimination singleElimination) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("tournament", singleElimination);
        modelAndView.setViewName("tournament/page");
        return modelAndView;
    }

    @GetMapping(value = "/tournaments/create", params = { "SINGLEELIMINATION" })
    public ModelAndView tournamentCreation() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("tournament", new SingleElimination());
        modelAndView.setViewName("tournament/createsingleelimination");
        return modelAndView;
    }

    @PostMapping(value = "/tournaments/create", params = { "SINGLEELIMINATION" })
    public ModelAndView createSingleElimination(@Valid SingleElimination singleElimination, BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {

            modelAndView.addObject("tournament", new SingleElimination());
            modelAndView.setViewName("tournament/create");
        } else {

            singleEliminationService.saveTournament(singleElimination);
            redirectAttributes.addFlashAttribute("successMessage", "Tournament has been created");
            redirectAttributes.addFlashAttribute("tournamentFormats", TournamentFormats.values());
            modelAndView.setViewName("redirect:/tournaments/create");
        }

        return modelAndView;
    }

    @Override
    public ModelAndView getJoinTournament(SingleElimination tournament) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("tournament", tournament);
        modelAndView.addObject("teams", teamService.getAllTeams());
        modelAndView.setViewName("tournament/addteam");
        return modelAndView;
    }

    @Override
    public ModelAndView postJoinTournament(SingleElimination tournament, long id) {
        ModelAndView modelAndView = new ModelAndView();
        Team team = teamService.findTeamById(id);
        try {
            singleEliminationService.checkTeamInTournament(tournament, team);
        } catch (TeamAlreadyInTournamentException e) {
            modelAndView.addObject("failMessage", "Team is already in tournament");
            modelAndView.addObject("tournament", tournament);
            modelAndView.addObject("teams", teamService.getAllTeams());
            modelAndView.setViewName("tournament/addteam");
            return modelAndView;
        }
        try {
            singleEliminationService.checkPlayerTeamInTournament(tournament, team);
        } catch (PlayerOfTeamAlreadyInTournamentException e) {

            modelAndView.addObject("failMessage", "A player of the team is already in tournament");
            modelAndView.addObject("tournament", tournament);
            modelAndView.addObject("teams", teamService.getAllTeams());
            modelAndView.setViewName("tournament/addteam");
            return modelAndView;
        }
        singleEliminationService.addTeamToTournament(tournament, team);
        singleEliminationService.addPlayer(tournament, team.getPlayerA());
        singleEliminationService.addPlayer(tournament, team.getPlayerB());
        modelAndView.addObject("tournament", tournament);
        modelAndView.addObject("teams", teamService.getAllTeams());
        modelAndView.addObject("successMessage", "Team was added to tournament");
        modelAndView.setViewName("tournament/addteam");
        return modelAndView;
    }

    @Override
    public ModelAndView getBracket(SingleElimination tournament) {
        ModelAndView modelAndView = new ModelAndView();
        singleEliminationService.createTournamentTree(tournament);
        modelAndView.addObject("tournament", singleEliminationService.getTournamentById(tournament.getTournamentId()));
        modelAndView.setViewName("tournament/tree");
        return modelAndView;
    }
}
