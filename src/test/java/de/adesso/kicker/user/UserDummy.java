package de.adesso.kicker.user;

import junit.framework.TestCase;

public class UserDummy extends TestCase {

    public User defaultUser() {
        return new User("Peter",  "Meier", "test@mail", "password");
    }

    public User alternateUser() { return new User("Hans", "Meier", "second@mail", "password2"); }

    public User alternateUser1() { return new User("Markus", "Franz", "third@mail", "password3"); }

    public User alternateUser2() { return new User("Jan", "Müller", "fourht@mail", "password4"); }

    public User alternateUser3() { return new User("Hans", "Müller", "sdfg@mail", "password4"); }

    public User alternateUser4() { return new User("Augustus", "Müller", "sdasdfsdfg@mail", "password4"); }

    public User nullUser() {
        return null;
    }

}
