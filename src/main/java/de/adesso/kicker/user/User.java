package de.adesso.kicker.user;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user")
public class User {

    @Id
    private String userId;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String email;

    private long wins;

    private long losses;

    public User() {
    }

    public User(String userId, String firstName, String lastName, String email) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.wins = 0;
        this.losses = 0;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
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

        this.losses += 1;
    }

    public void setWins(long wins) {
        this.wins = wins;
    }

    public void setLosses(long losses) {
        this.losses = losses;
    }

    @Override
    public String toString() {
        return "User{" + "userId='" + userId + '\'' + ", firstName='" + firstName + '\'' + ", lastName='" + lastName
                + '\'' + ", email='" + email + '\'' + ", wins=" + wins + ", losses=" + losses + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        User user = (User) o;
        return userId == user.userId && active == user.active && wins == user.wins && losses == user.losses
                && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName)
                && Objects.equals(password, user.password) && Objects.equals(email, user.email)
                && Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, firstName, lastName, password, email, active, wins, losses, roles);
    }
}
