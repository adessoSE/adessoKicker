package de.adesso.adessoKicker.objects;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;

@Entity
@Table(name = "team")
public class Team implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long teamId;

    @NotNull
    @Size(min=1, max=30, message="Der Teamname muss zwischen 1-30 Zeichen lang sein.")
    private String teamName;

    private long teamWins;

    private long teamLosses;

    @OneToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    private User playerA;

    @OneToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    private User playerB;

    public Team() {
    	teamName = new String();
    	playerA = new User();
    	playerB = new User();
    	teamWins = 0;
    	teamLosses = 0;
    }

    public Team(String teamName, User playerA, User playerB) {

        this.teamName = teamName;
        this.playerA = playerA;
        this.playerB = playerB;
        this.teamWins = 0;
        this.teamLosses = 0;
    }

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public long getTeamWins() {
        return teamWins;
    }

    public void setTeamWins(long teamWins) {
        this.teamWins = teamWins;
    }

    public long getTeamLosses() {
        return teamLosses;
    }

    public void setTeamLosses(long teamLosses) {
        this.teamLosses = teamLosses;
    }

    public User getPlayerA() {
        return playerA;
    }

    public void setPlayerA(User playerA) {
        this.playerA = playerA;
    }

    public User getPlayerB() {
        return playerB;
    }

    public void setPlayerB(User playerB) {
        this.playerB = playerB;
    }

    @Override
    public String toString() {
        return "Team{" +
                "teamId=" + teamId +
                ", teamName='" + teamName + '\'' +
                ", teamWins=" + teamWins +
                ", teamLosses=" + teamLosses +
                '}';
    }
}