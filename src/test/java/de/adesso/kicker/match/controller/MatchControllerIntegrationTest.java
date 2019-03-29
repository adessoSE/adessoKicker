package de.adesso.kicker.match.controller;

import de.adesso.kicker.email.SendVerificationMailService;
import de.adesso.kicker.match.persistence.MatchRepository;
import de.adesso.kicker.notification.matchverificationrequest.persistence.MatchVerificationRequestRepository;
import de.adesso.kicker.notification.matchverificationrequest.service.events.MatchVerificationSentEvent;
import de.adesso.kicker.user.persistence.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@TestPropertySource(locations = "classpath:application-integration-test.properties")
@SpringBootTest
@AutoConfigureMockMvc
class MatchControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MatchVerificationRequestRepository matchVerificationRequestRepository;

    @MockBean
    private SendVerificationMailService sendVerificationMailService;

    @Test
    @DisplayName("Assures that adding a match works through all layers")
    @WithMockUser(username = "user1")
    void addMatchWorksThroughAllLayers() throws Exception {
        // given
        willDoNothing().given(sendVerificationMailService).sendVerification(any(MatchVerificationSentEvent.class));
        var user1 = userRepository.findById("user1").orElseThrow();
        var user2 = userRepository.findById("user2").orElseThrow();

        // when
        mockMvc.perform(post("/matches/add").with(csrf())
                .param("date", LocalDate.now().toString())
                .param("teamAPlayer1.userId", user1.getUserId())
                .param("teamAPlayer1.firstName", user1.getFirstName())
                .param("teamAPlayer1.lastName", user1.getLastName())
                .param("teamAPlayer1.email", user1.getEmail())
                .param("teamAPlayer1.emailNotifications", "true")
                .param("teamAPlayer1.statistic.statisticId", user1.getStatistic().getStatisticId())
                .param("teamAPlayer1.statistic.rating", String.valueOf(user1.getStatistic().getRating()))
                .param("teamAPlayer1.statistic.rank", String.valueOf(user1.getStatistic().getRank()))
                .param("teamAPlayer1.wins", String.valueOf(user1.getStatistic().getWins()))
                .param("teamAPlayer1.losses", String.valueOf(user1.getStatistic().getLosses()))
                .param("teamBPlayer1.userId", user2.getUserId())
                .param("teamBPlayer1.firstName", user2.getFirstName())
                .param("teamBPlayer1.lastName", user2.getLastName())
                .param("teamBPlayer1.email", user2.getEmail())
                .param("teamBPlayer1.emailNotifications", "true")
                .param("teamBPlayer1.statistic.statisticId", user2.getStatistic().getStatisticId())
                .param("teamBPlayer1.statistic.rating", String.valueOf(user2.getStatistic().getRating()))
                .param("teamBPlayer1.statistic.rank", String.valueOf(user2.getStatistic().getRank()))
                .param("teamBPlayer1.wins", String.valueOf(user2.getStatistic().getWins()))
                .param("teamBPlayer1.losses", String.valueOf(user2.getStatistic().getLosses()))
                .param("winnerTeamA", "true"));

        // then
        var matches = matchRepository.findAll();
        var match = matchRepository.findById("1").orElseThrow();
        var notifications = matchVerificationRequestRepository.getAllByMatch(match);
        assertEquals(user1.getUserId(), match.getTeamAPlayer1().getUserId());
        assertEquals(user2.getUserId(), match.getTeamBPlayer1().getUserId());
        assertNotNull(notifications);
        then(sendVerificationMailService).should(times(1)).sendVerification(any(MatchVerificationSentEvent.class));
    }
}
