package de.adesso.kicker.tournament.singleelimination;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.adesso.kicker.team.Team;
import de.adesso.kicker.team.TeamService;
import de.adesso.kicker.tournament.TournamentControllerInterface;
import de.adesso.kicker.tournament.TournamentFormats;
import de.adesso.kicker.user.User;
import de.adesso.kicker.user.UserService;

@Controller
public class SingleEliminationController implements TournamentControllerInterface<SingleElimination> {

    private SingleEliminationService singleEliminationService;
    private TeamService teamService;
    private UserService userService;

    @Autowired
    public SingleEliminationController(SingleEliminationService singleEliminationService, TeamService teamService,
            UserService userService) {

        this.singleEliminationService = singleEliminationService;
        this.teamService = teamService;
        this.userService = userService;
    }

    @Override
    public Class<SingleElimination> appliesTo() {
        return SingleElimination.class;
    }

    @Override
    @Transactional
    public ModelAndView getPage(SingleElimination singleElimination) {
        User loggedInUser = userService.getLoggedInUser();
        ModelAndView modelAndView = new ModelAndView();
        try {
            singleEliminationService.getTournamentPage(singleElimination, loggedInUser);
        } catch (PlayerInTournamentException e) {
            modelAndView.addObject("tournament", singleElimination);
            modelAndView.addObject("teams", null);
            modelAndView.addObject("user", loggedInUser);
            modelAndView.setViewName("tournament/singleeliminationpage");
            return modelAndView;
        }
        modelAndView.addObject("tournament", singleElimination);
        modelAndView.addObject("teams", teamService.findTeamsByPlayer(loggedInUser));
        modelAndView.addObject("user", loggedInUser);
        modelAndView.setViewName("tournament/singleeliminationpage");
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
            singleEliminationService.createSingleEliminationTournament(singleElimination);
            redirectAttributes.addFlashAttribute("successMessage", "Tournament has been created");
            redirectAttributes.addFlashAttribute("tournamentFormats", TournamentFormats.values());
            modelAndView.setViewName("redirect:/tournaments/create");
        }
        return modelAndView;
    }

    @Override
    public ModelAndView postJoinTournament(SingleElimination tournament, String id) {
        ModelAndView modelAndView = new ModelAndView();
        Team team = teamService.findTeamById(id);
        User loggedInUser = userService.getLoggedInUser();
        try {
            singleEliminationService.joinTournament(tournament, team);
        } catch (TeamAlreadyInTournamentException e) {
            modelAndView.addObject("user", loggedInUser);
            modelAndView.addObject("teams", teamService.findTeamsByPlayer(loggedInUser));
            modelAndView.addObject("failMessage", "Team is already in tournament");
            modelAndView.addObject("tournament", tournament);
            modelAndView.setViewName("tournament/singleeliminationpage");
            return modelAndView;
        } catch (PlayerOfTeamInTournamentException e) {
            modelAndView.addObject("user", loggedInUser);
            modelAndView.addObject("teams", teamService.findTeamsByPlayer(loggedInUser));
            modelAndView.addObject("failMessage", "A player of the team is already in tournament");
            modelAndView.addObject("tournament", tournament);
            modelAndView.setViewName("tournament/singleeliminationpage");
            return modelAndView;
        }
        modelAndView.addObject("tournament", tournament);
        modelAndView.addObject("teams", teamService.findTeamsByPlayer(loggedInUser));
        modelAndView.addObject("successMessage", "Team was added to tournament");
        modelAndView.setViewName("tournament/singleeliminationpage");
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
