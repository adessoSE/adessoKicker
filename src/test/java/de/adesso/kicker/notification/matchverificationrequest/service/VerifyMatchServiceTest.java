package de.adesso.kicker.notification.matchverificationrequest.service;

import de.adesso.kicker.events.MatchCreatedEventDummy;
import de.adesso.kicker.match.persistence.Match;
import de.adesso.kicker.match.persistence.MatchDummy;
import de.adesso.kicker.match.service.events.MatchDeclinedEvent;
import de.adesso.kicker.match.service.events.MatchVerifiedEvent;
import de.adesso.kicker.notification.matchverificationrequest.persistence.MatchVerificationRequest;
import de.adesso.kicker.notification.matchverificationrequest.persistence.MatchVerificationRequestDummy;
import de.adesso.kicker.notification.matchverificationrequest.persistence.MatchVerificationRequestRepository;
import de.adesso.kicker.user.persistence.UserDummy;
import de.adesso.kicker.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.TestPropertySource;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;

@TestPropertySource("classpath:application-test.properties")
class VerifyMatchServiceTest {

    @Mock
    MatchVerificationRequestRepository matchVerificationRequestRepository;

    @Mock
    ApplicationEventPublisher applicationEventPublisher;

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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("All requests that belong to the match should be deleted")
    void verifyMatchAndDeleteAllRequests() {
        // given
        var matchVerification = createMatchVerification();
        given(matchVerificationRequestRepository.getAllByMatch(matchVerification.getMatch()))
                .willReturn(createMatchVerificationList());

        // when
        verifyMatchService.acceptRequest(matchVerification);

        // then
        then(matchVerificationRequestRepository).should().deleteAll(any(List.class));
        then(applicationEventPublisher).should(times(1)).publishEvent(any(MatchVerifiedEvent.class));
    }

    @Test
    @DisplayName("All players of the opponent team should receive a verification request")
    void sendRequestsToOpponents() {
        // given
        var match = MatchDummy.match();
        var matchCreatedEvent = MatchCreatedEventDummy.matchCreatedEvent(match);

        // when
        verifyMatchService.sendRequests(matchCreatedEvent);

        // then
        then(matchVerificationRequestRepository).should(times(2)).save(any(MatchVerificationRequest.class));
    }

    @Test
    void sendRequestToOpponentsAsLoser() {
        var match = MatchDummy.matchTeamBWon();
        var matchCreatedEvent = MatchCreatedEventDummy.matchCreatedEvent(match);

        // when
        verifyMatchService.sendRequests(matchCreatedEvent);

        // then
        then(matchVerificationRequestRepository).should(times(1)).save(any(MatchVerificationRequest.class));
    }

    @Test
    @DisplayName("The match of the request and all requests that belong to that match should be deleted "
            + "and every player that is not the current user should be returned in a list")
    void deleteMatchAndRequestsAndReturnUsersToInform() {
        // given
        var matchVerification = createMatchVerification();
        given(matchVerificationRequestRepository.getAllByMatch(any(Match.class)))
                .willReturn(createMatchVerificationList());
        given(userService.getLoggedInUser()).willReturn(UserDummy.defaultUser());

        // when
        verifyMatchService.declineRequest(matchVerification);

        // then
        then(applicationEventPublisher).should(times(1)).publishEvent(any(MatchDeclinedEvent.class));
        then(matchVerificationRequestRepository).should().delete(any(MatchVerificationRequest.class));
        assertEquals(3, verifyMatchService.declineRequest(matchVerification).size());
    }
}