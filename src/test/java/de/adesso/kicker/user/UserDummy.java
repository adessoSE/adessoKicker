package de.adesso.kicker.user;

import de.adesso.kicker.statistics.ranking.RankingDummy;
import de.adesso.kicker.user.persistence.User;

public class UserDummy {

    public static User defaultUser() {
        return new User("Peter", "Meier", "test@mail", "password");
    }

    public static User alternateUser() {
        return new User("Hans", "Meier", "second@mail", "password2");
    }

    public static User alternateUser1() {
        return new User("Markus", "Franz", "third@mail", "password3");
    }

    public static User alternateUser2() {
        return new User("Jan", "Müller", "fourht@mail", "password4");
    }

    public static User userWithLowRating() {
        var user = new User("user", "User", "User", "user@mail");
        user.setRanking(RankingDummy.ranking());
        return user;
    }

    public static User userWithHighRating() {
        var user = new User("user", "User", "User", "user@mail");
        user.setRanking(RankingDummy.highRating());
        return user;
    }

    public static User userWithVeryHighRating() {
        var user = new User("user", "User", "User", "user@mail");
        user.setRanking(RankingDummy.veryHighRating());
        return user;
    }
}
