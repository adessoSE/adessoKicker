package de.adesso.kicker.notification.teamjoinrequest;

import de.adesso.kicker.notification.NotificationRepository;
import de.adesso.kicker.team.Team;
import de.adesso.kicker.team.TeamDummy;
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
import static org.mockito.Mockito.when;

public class TeamJoinRequstServiceTest {

    @InjectMocks
    TeamJoinRequestService teamJoinRequestService;

    @Mock
    NotificationRepository notificationRepository;
    @Mock
    UserService userService;
    @Mock
    TeamService teamService;

    //Dumies
    TeamDummy teamDumy = new TeamDummy();
    UserDummy userDummy = new UserDummy();
    TeamJoinRequestDummy teamJoinRequestDummy = new TeamJoinRequestDummy();

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        when(userService.getUserById(1)).thenReturn(userDummy.defaultUser());
        when(userService.getUserById(2)).thenReturn(userDummy.defaultUser());
        when((TeamJoinRequest)notificationRepository.findByNotificationId(anyLong())).thenReturn(teamJoinRequestDummy.defaultTeamJoinRequest());
    }

    @Test
    public void createTeamJoinRequest_Valid(){

        teamJoinRequestService.createTeamJoinRequest(1,2, "testTeam");
    }
    @Test
    public void createTeamJoinRequest_Invalid(){

        when(userService.getUserById(anyLong())).thenReturn(null);
        teamJoinRequestService.createTeamJoinRequest(1,2, "testTeam");
    }
    @Test
    public void acceptTeamJoinRequst_Valid(){

        teamJoinRequestService.acceptTeamJoinRequest(anyLong());
    }
    @Test
    public void acceptTeamJoinRequst_Invalid(){

        when((TeamJoinRequest)notificationRepository.findByNotificationId(anyLong())).thenReturn(null);
        teamJoinRequestService.acceptTeamJoinRequest(anyLong());
    }
    @Test
    public void  saveTeamJoinRequest_Valid(){

        teamJoinRequestService.saveTeamJoinRequest(1, 2, "testTeam");
    }
    @Test
    public void  saveTeamJoinRequest_Invalid(){

        teamJoinRequestService.saveTeamJoinRequest(-2, -1, "");
    }
    @Test
    public void  saveTeamJoinRequest_Repo_Valid(){

        teamJoinRequestService.saveTeamJoinRequest(teamJoinRequestDummy.defaultTeamJoinRequest());
    }
    @Test
    public void  saveTeamJoinRequest_Repo_Invalid(){

        teamJoinRequestService.saveTeamJoinRequest(null);
    }

}
