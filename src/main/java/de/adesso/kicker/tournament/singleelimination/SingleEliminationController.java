package de.adesso.kicker.tournament.singleelimination;

import javax.validation.Valid;


import de.adesso.kicker.notification.NotificationService;
import de.adesso.kicker.tournament.singleelimination.exception.PlayerInTournamentException;
import de.adesso.kicker.tournament.singleelimination.exception.PlayerOfTeamInTournamentException;
import de.adesso.kicker.tournament.singleelimination.exception.TeamAlreadyInTournamentException;
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
    private NotificationService notificationService;

    @Autowired
    public SingleEliminationController(SingleEliminationService singleEliminationService, TeamService teamService,
            UserService userService, NotificationService notificationService) {

        this.singleEliminationService = singleEliminationService;
        this.teamService = teamService;
        this.userService = userService;
        this.notificationService = notificationService;
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
            modelAndView.addObject("notifications", notificationService.getAllNotificationsByReceiver(loggedInUser.getUserId()));
            modelAndView.setViewName("tournament/singleeliminationpage");
            return modelAndView;
        }
        modelAndView.addObject("tournament", singleElimination);
        modelAndView.addObject("teams", teamService.findTeamsByPlayer(loggedInUser));
        modelAndView.addObject("user", loggedInUser);
        modelAndView.addObject("notifications", notificationService.getAllNotificationsByReceiver(loggedInUser.getUserId()));
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
        if (bindingResult.hasFieldErrors("tournamentName")) {
            redirectAttributes.addFlashAttribute("failMessage", "Invalid name");
            modelAndView.setViewName("redirect:/tournaments/create");
            return modelAndView;
        }

        if (bindingResult.hasFieldErrors("startDate")) {
            redirectAttributes.addFlashAttribute("failMessage", "Invalid Date");
            modelAndView.setViewName("redirect:/tournaments/create");
            return modelAndView;
        }

        singleEliminationService.createSingleEliminationTournament(singleElimination);
        redirectAttributes.addFlashAttribute("successMessage", "Tournament has been created");
        redirectAttributes.addFlashAttribute("tournamentFormats", TournamentFormats.values());
        modelAndView.setViewName("redirect:/tournaments/create");
        return modelAndView;
    }

    @Override
    public ModelAndView postJoinTournament(SingleElimination tournament, long id) {
        ModelAndView modelAndView = new ModelAndView();
        Team team = teamService.findTeamById(id);
        User loggedInUser = userService.getLoggedInUser();

        modelAndView.addObject("teams", teamService.findTeamsByPlayer(loggedInUser));
        modelAndView.addObject("tournament", tournament);
        try {
            singleEliminationService.joinTournament(tournament, team);
        } catch (TeamAlreadyInTournamentException e) {
            modelAndView.addObject("teams", teamService.findTeamsByPlayer(loggedInUser));
            modelAndView.addObject("failMessage", "Team is already in tournament");
            modelAndView.setViewName("tournament/singleeliminationpage");
            return modelAndView;
        } catch (PlayerOfTeamInTournamentException e) {
            modelAndView.addObject("teams", teamService.findTeamsByPlayer(loggedInUser));
            modelAndView.addObject("failMessage", "A player of the team is already in tournament");
            modelAndView.setViewName("tournament/singleeliminationpage");
            return modelAndView;
        }
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
