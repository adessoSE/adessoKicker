package de.adesso.kicker.user;

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
}
