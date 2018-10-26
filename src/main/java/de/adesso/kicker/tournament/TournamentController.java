package de.adesso.kicker.tournament;

import de.adesso.kicker.team.TeamService;
import de.adesso.kicker.tournament.lastmanstanding.LastManStandingController;
import de.adesso.kicker.tournament.singleelimination.SingleEliminationController;
import de.adesso.kicker.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TournamentController {

    private TournamentService tournamentService;

    private TeamService teamService;

    private SingleEliminationController singleEliminationController;

    private LastManStandingController lastManStandingController;

    private UserService userService;

    @Autowired
    public TournamentController(TournamentService tournamentService, TeamService teamService,
            SingleEliminationController singleEliminationController,
            LastManStandingController lastManStandingController, UserService userService) {

        this.tournamentService = tournamentService;
        this.teamService = teamService;
        this.singleEliminationController = singleEliminationController;
        this.lastManStandingController = lastManStandingController;
        this.userService = userService;
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
    public ModelAndView tournamentPage(@PathVariable("tournamentId") long id) {
        ModelAndView modelAndView = new ModelAndView();
        Tournament tournament = tournamentService.getTournamentById(id);
        String format = tournament.getFormat();
        switch (format) {

        case "SINGLEELIMINATION":
            return singleEliminationController.getSingleEliminationPage(tournament);

        case "LASTMANSTANDING":
            return lastManStandingController.getLastManStandingPage(tournament);

        default:
            modelAndView.setViewName("redirect:/tournaments/list");
            return modelAndView;
        }
    }
    
    @GetMapping("/tournaments/current")
    public ModelAndView tournamentPageCurrent() {
        ModelAndView modelAndView = new ModelAndView();
        Tournament tournament = tournamentService.getCurrentTournament();
        String format = tournament.getFormat();
        switch (format) {

        case "SINGLEELIMINATION":
            return singleEliminationController.getSingleEliminationPage(tournament);

        case "LASTMANSTANDING":
            return lastManStandingController.getLastManStandingPage(tournament);

        default:
            modelAndView.setViewName("redirect:/tournaments/list");
            return modelAndView;
        }
    }

    @GetMapping("tournaments/{tournamentId}/join")
    public ModelAndView showAddTeam(@PathVariable("tournamentId") long id) {
        ModelAndView modelAndView = new ModelAndView();
        Tournament tournament = tournamentService.getTournamentById(id);
        String format = tournament.getFormat();
        switch (format) {

        case ("SINGLEELIMINATION"):
            return singleEliminationController.joinTournament(tournament);

        case ("LASTMANSTANDING"):
           return lastManStandingController.joinTournament(tournament);

       default:
           modelAndView.addObject("tournaments", tournamentService.getAllTournaments());
           modelAndView.setViewName("tournaments");
           return modelAndView;
        }
    }

    @PostMapping("tournaments/{tournamentId}/join")
    public ModelAndView addToTournament(@PathVariable("tournamentId") long id, Long teamId, Long userId) {
        ModelAndView modelAndView = new ModelAndView();
        Tournament tournament = tournamentService.getTournamentById(id);
        String format = tournament.getFormat();
        switch (format) {
        case ("SINGLEELIMINATION"):
            return (singleEliminationController.addTeamToTournament(tournament, teamService.findTeamById(teamId)));

        case ("LASTMANSTANDING"):
            return lastManStandingController.addPlayerToTournament(tournament, userService.getUserById(userId));
        default:
            return modelAndView;
        }
    }

    @GetMapping("tournaments/{tournamentId}/tree")
    public ModelAndView showTournamentTree(@PathVariable("tournamentId") long id) {

        ModelAndView modelAndView = new ModelAndView();
        Tournament tournament = tournamentService.getTournamentById(id);
        String format = tournament.getFormat();
        switch (format) {
        case ("SINGLEELIMINATION"):
            modelAndView = singleEliminationController.showTree(tournament);
            return modelAndView;

        case ("LASTMANSTANDING"):
            modelAndView = lastManStandingController.showLivesMap(tournament);
            return modelAndView;

        default:
            modelAndView.setViewName("redirect:/tournaments");
            return modelAndView;
        }
    }

}
