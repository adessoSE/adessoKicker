package de.adesso.kicker.notification.teamjoinrequest;

import de.adesso.kicker.notification.teamjoinrequest.TeamJoinRequest;

import de.adesso.kicker.user.UserDummy;

public class TeamJoinRequestDummy {

    private UserDummy userDummy = new UserDummy();

    public TeamJoinRequest defaultTeamJoinRequest() { return new TeamJoinRequest("Test-Message 1", userDummy.defaultUser(), userDummy.alternateUser());
    }

    public TeamJoinRequest alternateTeamJoinRequest() {
        return new TeamJoinRequest("Test-Message 2", userDummy.alternateUser1(), userDummy.alternateUser2());
    }

    public TeamJoinRequest alternate1TeamJoinRequest() {
        return new TeamJoinRequest("Test-Message 3", userDummy.alternateUser2(), userDummy.alternateUser1());
    }

    public TeamJoinRequest nullTeamJoinRequest() {
        return null;
    }
}
