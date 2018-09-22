package de.adesso.adessoKicker.objects;


import javax.persistence.*;

@Entity
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long teamId;

    private String teamName;

    private long teamWins;

    private long teamLosses;

    private User[] players;


    /**
     *  Change from two variables to an array
     */
    @OneToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    private User playerA;

    @OneToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    private User playerB;

    public Team() {
        //User playerA = new User();
        //User playerB = new User();
    }

    public Team(String teamName, User playerA, User playerB) {

        this.teamName = teamName;
        this.playerA = playerA;
        this.playerB = playerB;
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