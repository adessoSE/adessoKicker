package de.adesso.kicker.match.persistence;

import de.adesso.kicker.user.persistence.UserDummy;

import java.time.LocalDate;

public class MatchDummy {
    public static Match match() {
        var match = new Match(LocalDate.now(), UserDummy.defaultUser(), UserDummy.alternateUser(),
                UserDummy.alternateUser1(), UserDummy.alternateUser2(), true);
        match.setMatchId("1");
        return match;
    }

    public static Match matchTeamBWon() {
        return new Match(LocalDate.now(), UserDummy.defaultUser(), UserDummy.alternateUser(), false);
    }

    public static Match matchWithEqualPlayerA1B1() {
        return new Match(LocalDate.now(), UserDummy.defaultUser(), UserDummy.defaultUser(), true);
    }

    public static Match matchWithEqualPlayerA1B2() {
        return new Match(LocalDate.now(), UserDummy.defaultUser(), UserDummy.alternateUser(),
                UserDummy.alternateUser2(), UserDummy.defaultUser(), true);
    }

    public static Match matchWithEqualPlayerA2B2() {
        return new Match(LocalDate.now(), UserDummy.defaultUser(), UserDummy.alternateUser2(),
                UserDummy.alternateUser1(), UserDummy.alternateUser2(), true);
    }

    public static Match matchWithSamePlayerTeamA() {
        return new Match(LocalDate.now(), UserDummy.defaultUser(), UserDummy.defaultUser(), UserDummy.alternateUser(),
                UserDummy.alternateUser1(), true);
    }

    public static Match matchWithEqualPlayerTeamB() {
        return new Match(LocalDate.now(), UserDummy.defaultUser(), UserDummy.alternateUser(),
                UserDummy.alternateUser1(), UserDummy.alternateUser1(), true);
    }

    public static Match matchWithoutDefaultUserAsPlayerA1() {
        return new Match(LocalDate.now(), UserDummy.alternateUser1(), UserDummy.alternateUser2(), true);
    }

    public static Match matchWithFutureDate() {
        return new Match(LocalDate.now().plusDays(2), UserDummy.defaultUser(), UserDummy.alternateUser(), true);
    }

    public static Match matchWithLowRating() {
        return new Match(LocalDate.now(), UserDummy.userWithLowRating(), UserDummy.userWithLowRating(), true);
    }

    public static Match matchWithHighRating() {
        return new Match(LocalDate.now(), UserDummy.userWithHighRating(), UserDummy.userWithHighRating(), true);
    }

    public static Match matchWithVeryHighRating() {
        return new Match(LocalDate.now(), UserDummy.userWithVeryHighRating(), UserDummy.userWithVeryHighRating(), true);
    }

    public static Match matchWithPlayersInDifferentRatingRanges() {
        return new Match(LocalDate.now(), UserDummy.userWithVeryHighRating(), UserDummy.userWithLowRating(), false);
    }
}
