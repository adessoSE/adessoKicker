package de.adesso.kicker.tournament;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import de.adesso.kicker.notification.NotificationService;
import de.adesso.kicker.user.User;
import de.adesso.kicker.user.UserService;

@Controller
public class TournamentController {

    private TournamentService tournamentService;
    private UserService userService;
    private NotificationService notificationService;

    @Autowired
    public TournamentController(TournamentService tournamentService, NotificationService notificationService,
            UserService userService) {
        this.tournamentService = tournamentService;
        this.notificationService = notificationService;
        this.userService = userService;
    }

    @GetMapping(value = "/tournaments/create")
    public ModelAndView createTournament() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("tournamentFormats", TournamentFormats.values());
        modelAndView.setViewName("tournament/create");
        return modelAndView;
    }

    @GetMapping("/tournaments")
    public ModelAndView showTournamentList() {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.getLoggedInUser();
        modelAndView.addObject("tournaments", tournamentService.getAllTournaments());
        modelAndView.addObject("user", user);
        modelAndView.addObject("notifications", notificationService.getAllNotificationsByReceiver(user.getUserId()));
        modelAndView.setViewName("tournament/list");
        return modelAndView;
    }

    @GetMapping("/tournaments/{tournamentId}")
    public ModelAndView getTournamentPage(@PathVariable("tournamentId") long id) {
        Tournament tournament = tournamentService.getTournamentById(id);
        return tournamentService.getPage(tournament);
    }

    @GetMapping("/tournaments/current")
    public ModelAndView tournamentPageCurrent() {
        ModelAndView modelAndView = new ModelAndView();
        Tournament tournament = tournamentService.getCurrentTournament();
        return tournamentService.getPage(tournament);
    }

//    @GetMapping("tournaments/{tournamentId}/join")
//    public ModelAndView getJoinTournament(@PathVariable("tournamentId") long id) {
//        Tournament tournament = tournamentService.getTournamentById(id);
//        return tournamentService.getJoinTournament(tournament);
//    }

    @PostMapping(value = "tournaments/{tournamentId}", params = "join")
    @ResponseBody
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
