package de.adesso.kicker.user;

import de.adesso.kicker.ranking.RankingDummy;

public class UserDummy {

    public static User defaultUser() {
        return new User("Peter", "Meier", "test@mail", "password", RankingDummy.ranking());
    }

    public static User alternateUser() {
        return new User("Hans", "Meier", "second@mail", "password2", RankingDummy.ranking());
    }

    public static User alternateUser1() {
        return new User("Markus", "Franz", "third@mail", "password3", RankingDummy.ranking());
    }

    public static User alternateUser2() {
        return new User("Jan", "Müller", "fourht@mail", "password4", RankingDummy.ranking());
    }

    public static User userWithLowRating() {
        return new User("user", "User", "User", "user@mail", RankingDummy.ranking());
    }

    public static User userWithHighRating() {
        return new User("user", "User", "User", "user@mail", RankingDummy.highRating());
    }

    public static User userWithVeryHighRating() {
        return new User("user", "User", "User", "user@mail", RankingDummy.veryHighRating());
    }
}
