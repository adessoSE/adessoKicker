package de.adesso.kicker.notification.matchcreationrequest;

import de.adesso.kicker.match.Match;
import de.adesso.kicker.match.MatchDummy;
import de.adesso.kicker.match.MatchService;
import de.adesso.kicker.notification.NotificationRepository;
import de.adesso.kicker.team.Team;
import de.adesso.kicker.team.TeamDummy;
import de.adesso.kicker.user.User;
import de.adesso.kicker.user.UserDummy;
import de.adesso.kicker.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Date;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class MatchCreationRequestServiceTest {

    @InjectMocks
    MatchCreationRequestService matchCreationRequestService;

    @Mock
    NotificationRepository notificationRepository;
    @Mock
    MatchService matchService;
    @Mock
    UserService userService;
    @Mock
    MatchCreationValidationRepository matchCreationValidationRepository;
    @Mock
    MatchCreationRequestRepository matchCreationRequestRepository;

    // dummies
    private User user = new UserDummy().alternateUser();
    private User otherUser = new UserDummy().alternateUser2();
    private Team team = new TeamDummy().defaultTeam();
    private Team otherTeam = new TeamDummy().alternateTeam();
    private Match match = new MatchDummy().defaultMatch();
    private MatchCreationRequest defaultMatchCreationRequest = new MatchCreationRequestDummy().defaultMatchCreationRequest();

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);

        match.setDate(new Date());
        match.setTime(new Date());

        when(userService.getLoggedInUser()).thenReturn(user);
        when(notificationRepository.findByNotificationId(-1l)).thenReturn(null);
        when(matchCreationRequestRepository.findByNotificationId(anyLong())).thenReturn(defaultMatchCreationRequest);
    }

    @Test
    void generateRequests_ValidInput() {

        match.setTeamA(new Team("test", user, match.getTeamA().getPlayerB()));
        matchCreationRequestService.generateMatchCreationRequests(match);
    }

    @Test
    void generateRequests_NullMatch() {

        matchCreationRequestService.generateMatchCreationRequests(null);
    }

    @Test
    void generateRequests_UserNotInTeamA() {

        match.setTeamB(new Team("test", user, match.getTeamB().getPlayerB()));
        matchCreationRequestService.generateMatchCreationRequests(match);
    }

    @Test
    void saveMatchCreationRequest_ValidInput() {

        matchCreationRequestService.saveMatchCreationRequest(user, otherUser, team, otherTeam, new Date(), new Date(),
                "der Kicker", new MatchCreationValidation());
    }

    @Test
    void saveMatchCreationRequest_SenderNull() {

        matchCreationRequestService.saveMatchCreationRequest(null, otherUser, team, otherTeam, new Date(), new Date(),
                "der Kicker", new MatchCreationValidation());
    }

    @Test
    void saveMatchCreationRequest_ReceiverNull() {

        matchCreationRequestService.saveMatchCreationRequest(user, null, team, otherTeam, new Date(), new Date(),
                "der Kicker", new MatchCreationValidation());
    }

    @Test
    void saveMatchCreationRequest_TeamANull() {

        matchCreationRequestService.saveMatchCreationRequest(user, otherUser, null, otherTeam, new Date(), new Date(),
                "der Kicker", new MatchCreationValidation());
    }

    @Test
    void saveMatchCreationRequest_TeamBNull() {

        matchCreationRequestService.saveMatchCreationRequest(user, otherUser, team, null, new Date(), new Date(),
                "der Kicker", new MatchCreationValidation());
    }

    @Test
    void saveMatchCreationRequest_DateTimeNull() {

        matchCreationRequestService.saveMatchCreationRequest(user, otherUser, team, otherTeam, null, null, "der Kicker",
                new MatchCreationValidation());
    }

    @Test
    void saveMatchCreationRequest_KickerNull() {

        matchCreationRequestService.saveMatchCreationRequest(user, otherUser, team, otherTeam, new Date(), new Date(),
                "", new MatchCreationValidation());
    }

    @Test
    void saveMatchCreationRequest_ValidationNull() {

        matchCreationRequestService.saveMatchCreationRequest(user, otherUser, team, otherTeam, new Date(), new Date(),
                "der Kicker", new MatchCreationValidation());
    }

    @Test
    void saveMatchCreationRequest_Repo_ValidInput() {

        matchCreationRequestService.saveMatchCreationRequest(defaultMatchCreationRequest);
    }

    @Test
    void saveMatchCreationRequest_Repo_InputNull() {

        matchCreationRequestService.saveMatchCreationRequest(null);
    }

    @Test
    void acceptMatchJoinRequest_ValidInput_IncreaseVerify() {

        matchCreationRequestService.acceptMatchJoinRequest(anyLong());
    }

    @Test
    void acceptMatchJoinRequest_ValidInput_GenerateMatch() {

        defaultMatchCreationRequest.matchCreationValidation.increaseNumVerified();
        defaultMatchCreationRequest.matchCreationValidation.increaseNumVerified();
        matchCreationRequestService.acceptMatchJoinRequest(anyLong());
    }

    @Test
    void acceptMatchJoinRequest_NullInput() {

        matchCreationRequestService.acceptMatchJoinRequest(-1);
    }

    @Test
    void declineMatchJoinRequest_ValidInput() {

        matchCreationRequestService.declineMatchJoinRequest(anyLong());
    }

    @Test
    void declineMatchJoinRequest_NullInput() {

        matchCreationRequestService.declineMatchJoinRequest(-1);
    }
}
