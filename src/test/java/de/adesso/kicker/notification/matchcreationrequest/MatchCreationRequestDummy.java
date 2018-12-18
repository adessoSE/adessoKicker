package de.adesso.kicker.notification.matchcreationrequest;

import de.adesso.kicker.team.TeamDummy;
import de.adesso.kicker.user.UserDummy;

import java.util.Date;

public class MatchCreationRequestDummy {

    private UserDummy userDummy = new UserDummy();
    private TeamDummy teamDummy = new TeamDummy();
    private MatchCreationValidationDummy matchCreationValidationDummy = new MatchCreationValidationDummy();

    public MatchCreationRequest defaultMatchCreationRequest(){

        return new MatchCreationRequest(
                userDummy.defaultUser(), userDummy.alternateUser1(), teamDummy.defaultTeam(),
                teamDummy.alternateTeam(),
                new Date(),
                new Date(),
                "Kicker 1",
                matchCreationValidationDummy.defaultMatchValidation()
        );
    }

    public MatchCreationRequest alternate1MatchCreationRequest(){

        return new MatchCreationRequest(
                userDummy.alternateUser1(), userDummy.alternateUser2(), teamDummy.alternateTeam(),
                teamDummy.alternateTeam2(),
                new Date(),
                new Date(),
                "Kicker 1",
                matchCreationValidationDummy.alternate1MatchValidation()
        );
    }

    public MatchCreationRequest nullMatchCreationRequest(){ return null; }
}
