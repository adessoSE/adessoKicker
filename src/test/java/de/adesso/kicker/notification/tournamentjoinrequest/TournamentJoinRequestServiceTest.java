package de.adesso.kicker.notification.tournamentjoinrequest;

import de.adesso.kicker.notification.NotificationRepository;
import de.adesso.kicker.team.Team;
import de.adesso.kicker.user.User;
import de.adesso.kicker.team.TeamDummy;
import de.adesso.kicker.tournament.Tournament;
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

class TournamentJoinRequestServiceTest {

    @InjectMocks
    TournamentJoinRequestService tournamentJoinRequestService;

    @Mock
    private UserService userService;
    @Mock
    private SingleEliminationService singleEliminationService;
    @Mock
    private NotificationRepository notificationRepository;

    // Dummys
    private Tournament singleElimination = new SingleElimDummy().defaultSingleElim();
    private Team team = new TeamDummy().defaultTeam();
    private User user = new UserDummy().defaultUser();
    private TournamentJoinRequest tournamentJoinRequest = new TournamentJoinRequestDummy().defaultTournamentJoinRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        team.getPlayerB().setUserId(1);
        when(userService.getUserById(team.getPlayerA().getUserId())).thenReturn(team.getPlayerA());
        when((TournamentJoinRequest) notificationRepository.findByNotificationId(anyLong()))
                .thenReturn(tournamentJoinRequest);
        when(!notificationRepository.existsById(anyLong())).thenReturn(true);
    }

    @Test
    void saveTournamentJoinRequest_SingleElimination() {

        tournamentJoinRequestService.saveTournamentJoinRequest(team.getPlayerA().getUserId(), team,
                (Tournament) singleElimination);
    }

    @Test
    void saveTournamentJoinRequest_TournamentJoinRequest() {

        tournamentJoinRequestService.saveTournamentJoinRequest(tournamentJoinRequest);
    }

    @Test
    void createTournamentJoinRequest_SingleElimination_Valid() {

        tournamentJoinRequestService.createTournamentJoinRequest(user.getUserId(), team,
                (Tournament) singleElimination);
    }

    @Test
    void createTournamentJoinRequest_SingleElimination_Null() {

        tournamentJoinRequestService.createTournamentJoinRequest(anyLong(), null, null);
    }

    @Test
    void acceptTournamentJoinRequest_SingleElimination_Valid() {

        tournamentJoinRequestService.acceptTournamentJoinRequest(tournamentJoinRequest.getNotificationId());
    }

    @Test
    void acceptTournamentJoinRequest_SingleElimination_Null() {

        tournamentJoinRequestService.acceptTournamentJoinRequest(-1);
    }

}