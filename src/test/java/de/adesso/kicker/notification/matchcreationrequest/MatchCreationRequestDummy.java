package de.adesso.kicker.notification.matchcreationrequest;

import de.adesso.kicker.notification.matchcreationrequest.MatchCreationRequest;
import de.adesso.kicker.notification.matchcreationrequest.MatchCreationValidation;
import de.adesso.kicker.team.TeamDummy;
import de.adesso.kicker.user.UserDummy;

import java.util.Date;

class MatchCreationRequestDummy {

    private TeamDummy teamDummy = new TeamDummy();
    private UserDummy userDummy = new UserDummy();
    private String kicker = "der Kicker";
    private MatchCreationValidation matchCreationValidation = new MatchCreationValidation();

    MatchCreationRequest defaultMatchCreationRequest() {
        return new MatchCreationRequest(userDummy.defaultUser(), userDummy.alternateUser(), teamDummy.defaultTeam(),
                teamDummy.alternateTeam(), new Date(), new Date(), kicker, matchCreationValidation);
    }

    MatchCreationRequest alternateMatchCreationRequest() {
        return new MatchCreationRequest(userDummy.alternateUser1(), userDummy.alternateUser2(),
                teamDummy.alternateTeam2(), teamDummy.alternateTeam3(), new Date(), new Date(), kicker,
                matchCreationValidation);
    }
}
