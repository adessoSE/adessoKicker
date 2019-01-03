package de.adesso.kicker.notification.teamjoinrequest;

import de.adesso.kicker.notification.NotificationRepository;
import de.adesso.kicker.notification.NotificationService;
import de.adesso.kicker.team.TeamService;
import de.adesso.kicker.user.User;
import de.adesso.kicker.user.UserDummy;
import de.adesso.kicker.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class TeamJoinRequestServiceTest {

    @InjectMocks
    TeamJoinRequestService teamJoinRequestService;

    @Mock
    NotificationRepository notificationRepository;
    @Mock
    NotificationService notificationService;
    @Mock
    UserService userService;
    @Mock
    TeamService teamService;

    //Dummy User
    UserDummy userDummy = new UserDummy();
    User user = userDummy.defaultUser();
    User otherUser = userDummy.alternateUser1();

    //Dummy TeamJoinRequest
    TeamJoinRequestDummy teamJoinRequestDummy = new TeamJoinRequestDummy();
    TeamJoinRequest teamJoinRequest = teamJoinRequestDummy.defaultTeamJoinRequest();
    TeamJoinRequest otherTeamJoinRequest = teamJoinRequestDummy.alternateTeamJoinRequest();

    @BeforeEach
    public void setUp(){

        MockitoAnnotations.initMocks(this);

        user.setUserId(1L);
        otherUser.setUserId(2L);

        when(userService.getUserById(anyLong())).thenReturn(null);
        when(userService.getUserById(eq(user.getUserId()))).thenReturn(user);
        when(userService.getUserById(eq(otherUser.getUserId()))).thenReturn(otherUser);

    }

    @Test
    public void saveTeamJoinRequest(){

        teamJoinRequestService.saveTeamJoinRequest(otherUser.getUserId(), user.getUserId(), anyString());
    }

    @Test
    public void saveTeamJoinRequest_TeamJoinRequest(){

        teamJoinRequestService.saveTeamJoinRequest(teamJoinRequest);
    }

    @Test
    public void createTeamJoinRequest_Valid(){

        teamJoinRequestService.createTeamJoinRequest(otherUser.getUserId(), user.getUserId(), anyString());
    }

    @Test
    public void createTeamJoinRequest_Null(){

        teamJoinRequestService.createTeamJoinRequest(-1L, -1l, anyString());
    }

    @Test
    public void acceptTeamJoinRequest_Valid(){

        teamJoinRequestService.acceptTeamJoinRequest(teamJoinRequest.getNotificationId());
    }

    @Test
    public void acceptTeamJoinRequest_Null(){

        teamJoinRequestService.acceptTeamJoinRequest(-1L);
    }
}
