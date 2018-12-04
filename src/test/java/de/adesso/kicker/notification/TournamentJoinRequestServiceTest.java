package de.adesso.kicker.notification;

import de.adesso.kicker.notification.tournamentjoinrequest.TournamentJoinRequest;
import de.adesso.kicker.notification.tournamentjoinrequest.TournamentJoinRequestService;
import de.adesso.kicker.team.Team;
import de.adesso.kicker.user.User;
import de.adesso.kicker.team.TeamDummy;
import de.adesso.kicker.tournament.Tournament;
import de.adesso.kicker.tournament.lastmanstanding.LastManDummy;
import de.adesso.kicker.tournament.singleelimination.SingleElimDummy;
import de.adesso.kicker.tournament.singleelimination.SingleEliminationService;
import de.adesso.kicker.user.UserDummy;
import de.adesso.kicker.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class TournamentJoinRequestServiceTest {

    @InjectMocks
    TournamentJoinRequestService tournamentJoinRequestService;

    @Mock
    private UserService userService;
    @Mock
    private SingleEliminationService singleEliminationService;
    @Mock
    private NotificationRepository notificationRepository;

    //Dummys
    Tournament singleElimination = new SingleElimDummy().defaultSingleElim();
    Team team = new TeamDummy().defaultTeam();
    User user = new UserDummy().defaultUser();
    TournamentJoinRequest tournamentJoinRequest = new TournamentJoinRequestDummy().defaultTournamentJoinRequest;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        team.getPlayerB().setUserId(1);
        when(userService.getUserById(team.getPlayerA().getUserId())).thenReturn(team.getPlayerA());
        when((TournamentJoinRequest)notificationRepository.findByNotificationId(anyLong())).thenReturn(tournamentJoinRequest);
        when(!notificationRepository.existsById(tournamentJoinRequest.notificationId)).thenReturn(true);
    }

    @Test
    public void saveTournamentJoinRequest_SingleElimination() {

        tournamentJoinRequestService.saveTournamentJoinRequest((Tournament)singleElimination, team.getPlayerA().getUserId(), team);
    }

    @Test
    public void saveTournamentJoinRequest_TournamentJoinRequest() {

        tournamentJoinRequestService.saveTournamentJoinRequest(tournamentJoinRequest);
    }

    @Test
    public void createTournamentJoinRequest_SingleElimination_Valid(){

        tournamentJoinRequestService.createTournamentJoinRequest((Tournament)singleElimination, user.getUserId(), team);
    }

    @Test
    public void createTournamentJoinRequest_SingleElimination_Null(){

        tournamentJoinRequestService.createTournamentJoinRequest(null, anyLong(), null);
    }

    @Test
    public void acceptTournamentJoinRequest_SingleElimination_Valid(){

        tournamentJoinRequestService.acceptTournamentJoinRequest(tournamentJoinRequest.getNotificationId());
    }

    @Test
    public void acceptTournamentJoinRequest_SingleElimination_Null(){

        tournamentJoinRequestService.acceptTournamentJoinRequest(-1);
    }

}