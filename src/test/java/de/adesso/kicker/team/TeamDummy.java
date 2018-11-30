package de.adesso.kicker.team;

import de.adesso.kicker.user.UserDummy;

public class TeamDummy {

    private UserDummy userDummy = new UserDummy();

    public Team defaultTeam() {
        return new Team("Team1", userDummy.defaultUser(), userDummy.alternateUser());
    }

    public Team alternateTeam() {
        return new Team("Team2", userDummy.alternateUser1(), userDummy.alternateUser2());
    }

    public Team alternateTeam2() {
        return new Team("Team3", userDummy.alternateUser3(), userDummy.alternateUser4());
    }

    public Team alternateTeam3() {
        return new Team("Team4", userDummy.alternateUser3(), userDummy.alternateUser4());
    }

}
