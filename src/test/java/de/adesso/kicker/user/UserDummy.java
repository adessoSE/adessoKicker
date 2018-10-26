package de.adesso.kicker.user;

import junit.framework.TestCase;

public class UserDummy extends TestCase {

    public User defaultUser() {
        return new User("Peter",  "Meier", "test@mail", "password");
    }

    public User alternateUser() { return new User("Hans", "Meier", "second@mail", "password2"); }

    public User nullUser() {
        return null;
    }

}
