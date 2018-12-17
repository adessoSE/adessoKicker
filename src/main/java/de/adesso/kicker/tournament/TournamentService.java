package de.adesso.kicker.tournament;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import de.adesso.kicker.notification.tournamentjoinrequest.TournamentJoinRequestService;
import de.adesso.kicker.team.TeamService;
import de.adesso.kicker.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import de.adesso.kicker.user.User;

@Service
public class TournamentService {

    private TournamentJoinRequestService tournamentJoinRequestService;
    private UserService userService;
    private TeamService teamService;
    private TournamentRepository tournamentRepository;
    private List<TournamentControllerInterface> tournamentControllerInterfaces;

    @Autowired
    public TournamentService(TournamentRepository tournamentRepository, UserService userService, TeamService teamService,
            List<TournamentControllerInterface> tournamentControllerInterfaces, TournamentJoinRequestService tournamentJoinRequestService) {

        this.tournamentJoinRequestService = tournamentJoinRequestService;
        this.tournamentRepository = tournamentRepository;
        this.tournamentControllerInterfaces = tournamentControllerInterfaces;
        this.userService = userService;
        this.teamService = teamService;
    }

    public TournamentService(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    private Map<Class<? extends Tournament>, TournamentControllerInterface> controllerInterfaceMap = new HashMap<>();

    public TournamentService() {
    }

    @PostConstruct
    @SuppressWarnings("unchecked")
    public void init() {
        for (TournamentControllerInterface tournamentControllerInterface : tournamentControllerInterfaces) {
            controllerInterfaceMap.put(tournamentControllerInterface.appliesTo(), tournamentControllerInterface);
        }
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public ModelAndView getPage(Tournament tournament) {
        return controllerInterfaceMap.get(tournament.getClass()).getPage(tournament);
    }

//    @SuppressWarnings("unchecked")
//    @Transactional
//    public ModelAndView getJoinTournament(Tournament tournament) {
//        return controllerInterfaceMap.get(tournament.getClass()).getJoinTournament(tournament);
//    }

    @SuppressWarnings("unchecked")
    @Transactional
    public ModelAndView postJoinTournament(Tournament tournament, long id) {
        tournamentJoinRequestService.saveTournamentJoinRequest(userService.getLoggedInUser().getUserId(), teamService.getTeamById(id), tournament);
        return controllerInterfaceMap.get(tournament.getClass()).postJoinTournament(tournament, id);
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public ModelAndView getBracket(Tournament tournament) {
        return controllerInterfaceMap.get(tournament.getClass()).getBracket(tournament);
    }

    /**
     * Sets Tournament.finished to true
     *
     * @param tournament Tournament
     */
    public void setTournamentFinished(Tournament tournament) {

        tournament.setFinished(true);
        tournamentRepository.save(tournament);
    }

    /**
     * Returns a List of Tournaments where finished == false
     *
     * @return List<Tournament>
     */
    public List<Tournament> listRunningTournaments() {

        return tournamentRepository.findByFinished(false);
    }

    /**
     * Returns a list of all tournaments
     *
     * @return tournaments List<Tournaments>
     */
    public List<Tournament> getAllTournaments() {

        List<Tournament> tournaments = new ArrayList<>();
        tournamentRepository.findAll().forEach(tournaments::add);
        return tournaments;
    }

    /**
     * Returns a Tournament with the specified id
     *
     * @param id long
     * @return Tournament
     */
    public Tournament getTournamentById(Long id) {

        return tournamentRepository.findByTournamentId(id);
    }

    public Tournament getCurrentTournament() {

        List<Tournament> tournaments = new ArrayList<>();
        tournamentRepository.findByFinishedFalseOrderByStartDateAsc().forEach(tournaments::add);
        return tournaments.get(0);
    }

    /**
     * Saves a Tournament in the Tournament table
     *
     * @param tournament Tournament
     */
    public void saveTournament(Tournament tournament) {

        tournamentRepository.save(tournament);
    }

    /**
     * Adds a player to the list of players that are in the tournament
     *
     * @param tournament Tournament
     * @param player     User
     */
    public void addPlayer(Tournament tournament, User player) {

        tournament.getPlayers().add(player);
        saveTournament(tournament);
    }

}
