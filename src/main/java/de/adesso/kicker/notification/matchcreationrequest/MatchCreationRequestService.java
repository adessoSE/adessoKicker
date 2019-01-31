package de.adesso.kicker.notification.matchcreationrequest;

import de.adesso.kicker.match.Match;
import de.adesso.kicker.match.MatchService;
import de.adesso.kicker.notification.NotificationRepository;
import de.adesso.kicker.team.Team;
import de.adesso.kicker.user.User;
import de.adesso.kicker.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MatchCreationRequestService {

    private NotificationRepository notificationRepository;
    private MatchService matchService;
    private UserService userService;
    private MatchCreationValidationRepository matchCreationValidationRepository;
    private MatchCreationRequestRepository matchCreationRequestRepository;

    @Autowired
    public MatchCreationRequestService(NotificationRepository notificationRepository, UserService userService,
            MatchCreationValidationRepository matchCreationValidationRepository,
            MatchCreationRequestRepository matchCreationRequestRepository, MatchService matchService) {

        this.notificationRepository = notificationRepository;
        this.matchService = matchService;
        this.userService = userService;
        this.matchCreationValidationRepository = matchCreationValidationRepository;
        this.matchCreationRequestRepository = matchCreationRequestRepository;
    }

    public void generateMatchCreationRequests(Match match) {

        User sender = userService.getLoggedInUser();
        MatchCreationValidation matchCreationValidation = new MatchCreationValidation();

        if (match == null) {
            System.err.println(
                    "ERROR at 'MatchCreationRequestService' --> 'generateMatchCreationRequests()' : Match is NULL");
        } else {

            matchCreationValidationRepository.save(matchCreationValidation);

            if (match.getTeamA().getPlayerA() != sender && match.getTeamA().getPlayerB() != sender) {
                System.err.println(
                        "ERROR at 'MatchCreationRequestService' --> 'generateMatchCreationRequests()' : logged in User is not in Team A");
            } else if (match.getTeamA().getPlayerA() == sender) {

                saveMatchCreationRequest(sender, match.getTeamA().getPlayerB(), match.getTeamA(), match.getTeamB(),
                        match.getDate(), match.getTime(), match.getKicker(), matchCreationValidation);
                sendMatchCreationRequestToTeam(sender, match.getTeamB(), match.getTeamA(), match.getTeamB(),
                        match.getDate(), match.getTime(), match.getKicker(), matchCreationValidation);
            } else if (match.getTeamA().getPlayerB() == sender) {

                saveMatchCreationRequest(sender, match.getTeamA().getPlayerA(), match.getTeamA(), match.getTeamB(),
                        match.getDate(), match.getTime(), match.getKicker(), matchCreationValidation);
                sendMatchCreationRequestToTeam(sender, match.getTeamB(), match.getTeamA(), match.getTeamB(),
                        match.getDate(), match.getTime(), match.getKicker(), matchCreationValidation);
            }
        }
    }

    private void sendMatchCreationRequestToTeam(User sender, Team receiver, Team teamA, Team teamB, Date date,
            Date time, String kicker, MatchCreationValidation matchCreationValidation) {

        saveMatchCreationRequest(sender, receiver.getPlayerA(), teamA, teamB, date, time, kicker,
                matchCreationValidation);
        saveMatchCreationRequest(sender, receiver.getPlayerB(), teamA, teamB, date, time, kicker,
                matchCreationValidation);
    }

    public MatchCreationRequest saveMatchCreationRequest(User sender, User receiver, Team teamA, Team teamB, Date date,
            Date time, String kicker, MatchCreationValidation matchCreationValidation) {

        if (sender == null) {
            System.err.println(
                    "ERROR at 'MatchCreationRequestService' --> 'saveMatchCreationRequest()' : sender is NULL");
            return null;
        }
        if (receiver == null) {
            System.err.println(
                    "ERROR at 'MatchCreationRequestService' --> 'saveMatchCreationRequest()' : receiver is NULL");
            return null;
        }
        if (teamA == null) {
            System.err.println(
                    "ERROR at 'MatchCreationRequestService' --> 'saveMatchCreationRequest()' : Team A is NULL");
            return null;
        }
        if (teamB == null) {
            System.err
                    .println("ERROR at 'MatchCreationRequestService' --> 'saveMatchCreationRequest()' : TeamB is NULL");
            return null;
        }
        if (date == null || time == null) {
            System.err.println(
                    "ERROR at 'MatchCreationRequestService' --> 'saveMatchCreationRequest()' : date or time is NULL");
            return null;
        }
        if (kicker == null || kicker.equals("")) {
            System.err.println(
                    "ERROR at 'MatchCreationRequestService' --> 'saveMatchCreationRequest()' : Kicker is NULL");
            return null;
        }
        if (matchCreationValidation == null) {
            System.err.println(
                    "ERROR at 'MatchCreationRequestService' --> 'saveMatchCreationRequest()' : validation object is NULL");
            return null;
        }

        MatchCreationRequest request = new MatchCreationRequest(sender, receiver, teamA, teamB, date, time, kicker,
                matchCreationValidation);
        saveMatchCreationRequest(request);
        return request;
    }

    public void saveMatchCreationRequest(MatchCreationRequest matchCreationRequest) {

        if (matchCreationRequest == null) {
            System.err.println(
                    "ERROR at 'MatchCreationRequestService' --> 'saveMatchCreationRequest()' : matchCreationRequest is NULL");
        } else {
            notificationRepository.save(matchCreationRequest);
        }
    }

    public void acceptMatchJoinRequest(long notificationId) {

        MatchCreationRequest matchCreationRequest = (MatchCreationRequest) matchCreationRequestRepository
                .findByNotificationId(notificationId);
        if (matchCreationRequest == null) {
            System.err.println(
                    "ERROR at 'MatchCreationRequestService' --> 'acceptMatchJoinRequest()' : cannot find MatchCreationRequest with id: "
                            + notificationId);
        } else {
            MatchCreationValidation matchCreationValidation = matchCreationRequest.getMatchCreationValidation();
            if (matchCreationValidation.getNumVerified() < 2) {
                matchCreationValidation.increaseNumVerified();
                notificationRepository.delete(matchCreationRequest);
            } else {
                Match match = new Match(matchCreationRequest.getDate(), matchCreationRequest.getTime(),
                        matchCreationRequest.getKicker(), matchCreationRequest.getTeamA(),
                        matchCreationRequest.getTeamB());
                matchService.saveMatch(match);
                notificationRepository.delete(matchCreationRequest);
                matchCreationValidationRepository.delete(matchCreationValidation);
            }
        }
    }

    public void declineMatchJoinRequest(long notificationId) {

        MatchCreationRequest matchCreationRequest = matchCreationRequestRepository.findByNotificationId(notificationId);
        if (matchCreationRequest == null) {
            System.err.println(
                    "ERROR at 'MatchCreationRequestService' --> 'declineMatchJoinRequest()' : cannot find MatchCreationRequest with id: "
                            + notificationId);
        } else {
            MatchCreationValidation matchCreationValidation = matchCreationRequest.getMatchCreationValidation();
            List<MatchCreationRequest> requests = matchCreationRequestRepository
                    .getAllByMatchCreationValidation(matchCreationValidation);
            for (MatchCreationRequest req : requests) {

                notificationRepository.delete(req);
            }
            matchCreationValidationRepository.delete(matchCreationValidation);
        }
    }
}
