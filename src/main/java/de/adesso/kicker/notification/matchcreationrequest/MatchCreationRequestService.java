package de.adesso.kicker.notification.matchcreationrequest;

import de.adesso.kicker.match.Match;
import de.adesso.kicker.match.MatchService;
import de.adesso.kicker.notification.NotificationRepository;
import de.adesso.kicker.notification.teamjoinrequest.TeamJoinRequest;
import de.adesso.kicker.team.Team;
import de.adesso.kicker.user.User;
import de.adesso.kicker.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MatchCreationRequestService {

    private NotificationRepository notificationRepository;
    private MatchService matchService;
    private UserService userService;

    @Autowired
    public MatchCreationRequestService(NotificationRepository notificationRepository, MatchService matchService, UserService userService) {

        this.notificationRepository = notificationRepository;
        this.matchService = matchService;
        this.userService = userService;
    }

    public void generateMatchCreationRequests(Team teamA, Team teamB, Date date, Date time, String kicker) {

        User sender = userService.getLoggedInUser();
        MatchCreationValidation matchCreationValidation = new MatchCreationValidation();
        if(teamA.getPlayerA() == sender) {
            saveMatchCreationRequest(teamA, teamB, teamA.getPlayerB(), sender, date, time, kicker, matchCreationValidation);
        } else {
            saveMatchCreationRequest(teamA, teamB, teamA.getPlayerA(), sender, date, time, kicker, matchCreationValidation);
        }
        saveMatchCreationRequest(teamA, teamB, teamB.getPlayerA(), sender, date, time, kicker, matchCreationValidation);
        saveMatchCreationRequest(teamA, teamB, teamB.getPlayerB(), sender, date, time, kicker, matchCreationValidation);
    }

    public MatchCreationRequest saveMatchCreationRequest(Team teamA, Team teamB, User receiver, User sender, Date date, Date time, String kicker, MatchCreationValidation matchCreationValidation) {

        MatchCreationRequest request = new MatchCreationRequest(teamA, teamB, sender, receiver, date, time, kicker, matchCreationValidation);
        saveMatchCreationRequest(request);
        return request;
    }

    public void saveMatchCreationRequest(MatchCreationRequest matchCreationRequest) {
        notificationRepository.save(matchCreationRequest);
    }

    public void acceptMatchJoinRequest(long notificationId) {

        /*MatchCreationRequest request = (MatchCreationRequest) notificationRepository.findByNotificationId(notificationId);
        MatchCreationValidation matchCreationValidation = request.getMatchCreationValidation();
        if(matchCreationValidation.getNumVerified() < 2){
            matchCreationValidation.increaseNumVerified();
        } else {
            Match match = new Match(request.getDate(), request.getTime(), request.getKicker(), request.getTeamA(), request.getTeamB());
            matchService.saveMatch(match);
        }*/
    }
}
