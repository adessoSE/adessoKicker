package de.adesso.kicker.tournament.lastmanstanding;

import de.adesso.kicker.tournament.Tournament;
import de.adesso.kicker.user.User;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
public class LastManStanding extends Tournament {

    @ManyToOne(targetEntity = User.class)
    private User winner;

    private int maxLives;

    @ManyToMany(targetEntity = User.class, cascade = CascadeType.ALL)
    private List<User> remainingPlayers;

    @ElementCollection
    private Map<User, Integer> livesMap;

    public LastManStanding(String tournamentName) {

    }

    public LastManStanding() {

        this.setFormat("LASTMANSTANDING");
    }

    public User getWinner() {
        return winner;
    }

    public void setWinner(User winner) {
        this.winner = winner;
    }

    public int getMaxLives() {
        return maxLives;
    }

    public void setMaxLives(int maxLives) {
        this.maxLives = maxLives;
    }

    public Map<User, Integer> getLivesMap() {
        return livesMap;
    }

    public void setLivesMap(Map<User, Integer> livesMap) {
        this.livesMap = livesMap;
    }

    public List<User> getRemainingPlayers() {
        return remainingPlayers;
    }

    public void setRemainingPlayers(List<User> remainingPlayers) {
        this.remainingPlayers = remainingPlayers;
    }

    @Override
    public String toString() {
        return "LastManStanding{" + super.toString() + "winner=" + winner + ", livesMap=" + livesMap + '}';
    }
}
