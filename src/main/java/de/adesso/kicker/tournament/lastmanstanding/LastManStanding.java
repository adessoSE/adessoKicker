package de.adesso.kicker.tournament.lastmanstanding;

import de.adesso.kicker.tournament.Tournament;
import de.adesso.kicker.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class LastManStanding extends Tournament {

    private User winner;

    @ElementCollection(targetClass = Integer.class)
    private List<Integer> lives;

    public LastManStanding(String tournamentName) {

        super(tournamentName);
        this.lives = new ArrayList<>();
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

    public List<Integer> getLives() {
        return lives;
    }

    public void setLives(List<Integer> lives) {
        this.lives = lives;
    }

    @Override
    public String toString() {
        return "LastManStanding{" + super.toString() + "winner=" + winner + ", lives=" + lives + '}';
    }
}
