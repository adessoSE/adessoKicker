package de.adesso.kicker.notification.matchcreationrequest;

import de.adesso.kicker.match.MatchService;
import de.adesso.kicker.notification.NotificationRepository;
import de.adesso.kicker.team.Team;
import de.adesso.kicker.user.User;
import de.adesso.kicker.user.UserService;
import de.adesso.kicker.match.Match;
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
    public MatchCreationRequestService(NotificationRepository notificationRepository, UserService userService, MatchCreationValidationRepository matchCreationValidationRepository, MatchCreationRequestRepository matchCreationRequestRepository) {

        this.notificationRepository = notificationRepository;
        this.matchService = matchService;
        this.userService = userService;
        this.matchCreationValidationRepository = matchCreationValidationRepository;
        this.matchCreationRequestRepository = matchCreationRequestRepository;
    }

    public void generateMatchCreationRequests(Match match) {

        User sender = userService.getLoggedInUser();
        MatchCreationValidation matchCreationValidation = new MatchCreationValidation();

        if(match.getTeamA().getPlayerA() == sender) {
            saveMatchCreationRequest(sender, match.getTeamA().getPlayerB(), match.getTeamA(), match.getTeamB(), match.getDate(), match.getTime(), match.getKicker(), matchCreationValidation);
        } else if(match.getTeamA().getPlayerB() == sender) {
            saveMatchCreationRequest(sender, match.getTeamA().getPlayerA(), match.getTeamA(), match.getTeamB(), match.getDate(), match.getTime(), match.getKicker(), matchCreationValidation);
        } else { return;}
        saveMatchCreationRequest(sender, match.getTeamB().getPlayerA(), match.getTeamA(), match.getTeamB(), match.getDate(), match.getTime(), match.getKicker(), matchCreationValidation);
        saveMatchCreationRequest(sender, match.getTeamB().getPlayerB(), match.getTeamA(), match.getTeamB(), match.getDate(), match.getTime(), match.getKicker(), matchCreationValidation);
    }

    public MatchCreationRequest saveMatchCreationRequest(User sender, User receiver, Team teamA, Team teamB, Date date, Date time, String kicker, MatchCreationValidation matchCreationValidation) {

        MatchCreationRequest request = new MatchCreationRequest(sender, receiver, teamA, teamB, date, time, kicker, matchCreationValidation);
        saveMatchCreationRequest(request);
        return request;
    }

    public void saveMatchCreationRequest(MatchCreationRequest matchCreationRequest) {
        notificationRepository.save(matchCreationRequest);
    }

    public void acceptMatchJoinRequest(long notificationId) {

        MatchCreationRequest request = (MatchCreationRequest) notificationRepository.findByNotificationId(notificationId);
        MatchCreationValidation matchCreationValidation = request.getMatchCreationValidation();
        if(matchCreationValidation.getNumVerified() < 2){
            matchCreationValidation.increaseNumVerified();
            matchCreationRequestRepository.delete(request);
        } else {
            Match match = new Match(request.getDate(), request.getTime(), request.getKicker(), request.getTeamA(), request.getTeamB());
            matchService.saveMatch(match);
            matchCreationValidationRepository.delete(matchCreationValidation);
        }
    }

    public void declineMatchJoinRequest(long notificationID){

        MatchCreationRequest matchCreationRequest = matchCreationRequestRepository.findByNotificationId(notificationID);
        MatchCreationValidation matchCreationValidation = matchCreationRequest.getMatchCreationValidation();
        List<MatchCreationRequest> requests = matchCreationRequestRepository.getAllByMatchCreationValidation(matchCreationValidation);
        for (MatchCreationRequest req: requests) {
            req.setValidationNull();
            notificationRepository.delete(req);
        }
        matchCreationValidationRepository.delete(matchCreationValidation);
    }
}
