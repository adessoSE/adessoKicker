package de.adesso.kicker.notification.teamjoinrequest;

import de.adesso.kicker.user.UserDummy;

class TeamJoinRequestDummy {

    private UserDummy userDummy = new UserDummy();

    TeamJoinRequest defaultTeamJoinRequest() {
        return new TeamJoinRequest(userDummy.alternateUser(), userDummy.defaultUser(), "Test-Message 1");
    }

    TeamJoinRequest alternateTeamJoinRequest() {
        return new TeamJoinRequest(userDummy.alternateUser2(), userDummy.alternateUser1(), "Test-Message 2");
    }

    TeamJoinRequest alternate1TeamJoinRequest() {
        return new TeamJoinRequest(userDummy.alternateUser1(), userDummy.alternateUser2(), "Test-Message 3");
    }

    TeamJoinRequest nullTeamJoinRequest() {
        return null;
    }
}
