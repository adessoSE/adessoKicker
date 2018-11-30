package de.adesso.kicker.user;

import java.util.Objects;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import de.adesso.kicker.role.Role;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @NotNull
    @NotBlank(message = "Please enter your first name")
    @Size(max = 30, message = "Your first name can't be longer than 30 characters")
    private String firstName;

    @NotNull
    @NotBlank(message = "Please enter your last name")
    @Size(max = 30, message = "Your last name can't be longer than 30 characters")
    private String lastName;

    @NotNull
    @NotBlank(message = "Please enter a valid password")
    @Size(min = 8, max = 100, message = "Your password has to be between 8 and 100 characters long")
    private String password;

    @Email(message = "Please enter a valid e-Mail address")
    private String email;

    // Rename to verified
    private int active;

    private long wins;

    private long losses;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User() {
    }

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.active = 0;
        this.wins = 0;
        this.losses = 0;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
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

    public void setActive(int active) {
        this.active = active;
    }

    public int getActive() {
        return active;
    }

    public void increaseWins() {

        this.wins += 1;
    }

    public void increaseLosses() {

        this.losses += 1;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setWins(long wins) {
        this.wins = wins;
    }

    public void setLosses(long losses) {
        this.losses = losses;
    }

    @Override
    public String toString() {
        return "User{" + "userId=" + userId + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\''
                + ", password='" + password + '\'' + ", email='" + email + '\'' + ", active=" + active + ", wins="
                + wins + ", losses=" + losses + ", roles=" + roles + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId &&
                active == user.active &&
                wins == user.wins &&
                losses == user.losses &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, firstName, lastName, password, email, active, wins, losses, roles);
    }
}
