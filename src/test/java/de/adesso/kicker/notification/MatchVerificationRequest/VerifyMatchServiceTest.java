package de.adesso.kicker.notification.MatchVerificationRequest;

import de.adesso.kicker.match.Match;
import de.adesso.kicker.match.MatchDummy;
import de.adesso.kicker.user.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VerifyMatchServiceTest {

    @Mock
    MatchVerificationRequestRepository matchVerificationRequestRepository;

    @Mock
    UserService userService;

    @InjectMocks
    VerifyMatchService verifyMatchService;

    static MatchVerificationRequest createMatchVerification() {
        return MatchVerificationRequestDummy.matchVerificationRequest();
    }

    static List<MatchVerificationRequest> createMatchVerificationList() {
        return Collections.singletonList(MatchVerificationRequestDummy.matchVerificationRequest());
    }

    @BeforeAll
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("All requests that belong to the match should be deleted")
    void verifyMatchAndDeleteAllRequests() {
        // given
        var matchVerification = createMatchVerification();
        when(matchVerificationRequestRepository.getAllByMatch(matchVerification.getMatch()))
                .thenReturn(createMatchVerificationList());

        // when
        verifyMatchService.acceptRequest(matchVerification);

        // then
        verify(matchVerificationRequestRepository, times(createMatchVerificationList().size()))
                .delete(any(MatchVerificationRequest.class));
    }

    @Test
    @DisplayName("All players of the opponent team should receive a verification request")
    void sendRequestsToOpponents() {
        // given
        var match = MatchDummy.match();
        when(userService.getLoggedInUser()).thenReturn(match.getTeamAPlayer1());

        // when
        verifyMatchService.sendRequests(match);

        // then
        verify(matchVerificationRequestRepository, times(2)).save(any(MatchVerificationRequest.class));
    }

    @Test
    @DisplayName("The match of the request and all requests that belong to that match should be deleted "
            + "and every player that is not the current user should be returned in a list")
    void deleteMatchAndRequestsAndReturnUsersToInform() {
        // given
        var matchVerification = createMatchVerification();
        when(userService.getLoggedInUser()).thenReturn(matchVerification.getMatch().getTeamAPlayer1());
        when(matchVerificationRequestRepository.getAllByMatch(any(Match.class)))
                .thenReturn(createMatchVerificationList());

        // when
        verifyMatchService.declineRequest(matchVerification);

        // then
        verify(matchVerificationRequestRepository, times(2)).delete(any(MatchVerificationRequest.class));
        assertEquals(3, verifyMatchService.declineRequest(matchVerification).size());
    }
}