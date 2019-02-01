package de.adesso.kicker.match;

import de.adesso.kicker.user.UserDummy;

import java.time.LocalDate;

public class MatchDummy {
    private UserDummy userDummy = new UserDummy();
    public Match match() {
        return new Match(LocalDate.now(), userDummy.defaultUser(), userDummy.alternateUser(), userDummy.alternateUser1(), userDummy.alternateUser2());
    }

    public Match match_with_equal_player1() {
        return new Match(LocalDate.now(), userDummy.defaultUser(), userDummy.defaultUser());
    }

    public Match match_with_equal_player1_2() {
        return new Match(LocalDate.now(), userDummy.defaultUser(), userDummy.alternateUser(), userDummy.defaultUser(), userDummy.alternateUser2());
    }

    public Match Match_with_equal_player2_2() {
        return new Match(LocalDate.now(), userDummy.defaultUser(), userDummy.alternateUser(), userDummy.alternateUser1(), userDummy.alternateUser1());
    }

    public Match match_with_same_player_team() {
        return new Match(LocalDate.now(), userDummy.defaultUser(), userDummy.defaultUser(),null , null);
    }
}
