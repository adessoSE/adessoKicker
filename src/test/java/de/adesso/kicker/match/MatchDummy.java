package de.adesso.kicker.match;

import de.adesso.kicker.user.UserDummy;

import java.time.LocalDate;

public class MatchDummy {
    private UserDummy userDummy = new UserDummy();

    public Match match() {
        return new Match(LocalDate.now(), userDummy.defaultUser(), userDummy.alternateUser(),
                userDummy.alternateUser1(), userDummy.alternateUser2(), true);
    }

    public Match match_with_equal_player1() {
        return new Match(LocalDate.now(), userDummy.defaultUser(), userDummy.defaultUser(), true);
    }

    public Match match_with_equal_player1_2() {
        return new Match(LocalDate.now(), userDummy.defaultUser(), userDummy.alternateUser(), userDummy.defaultUser(),
                userDummy.alternateUser2(), true);
    }

    public Match match_with_equal_player2_2() {
        return new Match(LocalDate.now(), userDummy.defaultUser(), userDummy.alternateUser(),
                userDummy.alternateUser1(), userDummy.alternateUser1(), true);
    }

    public Match match_with_same_player_team() {
        return new Match(LocalDate.now(), userDummy.defaultUser(), userDummy.defaultUser(), null, null, true);
    }

    public Match match_without_default_user_player_1() {
        return new Match(LocalDate.now(), userDummy.alternateUser1(), userDummy.alternateUser2(), true);
    }

    public Match match_without_date() {
        return new Match(null, userDummy.defaultUser(), userDummy.alternateUser(), true);
    }

    public Match match_without_players() {
        return new Match(LocalDate.now(), null, null, true);
    }

    public Match match_with_only_player_a_1() {
        return new Match(LocalDate.now(), userDummy.defaultUser(), null, true);
    }

    public Match match_with_only_player_b_1() {
        return new Match(LocalDate.now(), null, userDummy.alternateUser(), true);
    }

    public Match match_without_winner() {
        return new Match(LocalDate.now(), userDummy.defaultUser(), userDummy.alternateUser(), null);
    }
}
