package de.adesso.adessoKicker.objects;

import javax.persistence.*;

@Entity
@Table(name = "tbUser")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idUser;

    private String firstName;

    private String lastName;

    private String email;

    private long wins = 0;

    private long losses = 0;

    public User() {}

    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.wins = 0;
        this.losses = 0;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public long getWins() {
        return wins;
    }

    public long getLosses() {
        return losses;
    }

    public void increaseWins() {

        this.wins += 1;
    }

    public void increaseLosses() {

        this.losses = getLosses() + 1;
    }

    @Override
    public String toString() {

        return "Name: " + this.getFirstName() + " " + this.getLastName() + ", e-Mail " + this.email + ", Wins: " + this.wins + ", Losses: " + this.losses + ", ID: " + this.idUser;
    }

}
