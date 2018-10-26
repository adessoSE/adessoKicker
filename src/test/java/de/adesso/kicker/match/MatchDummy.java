package de.adesso.kicker.match;

import de.adesso.kicker.team.Team;
import de.adesso.kicker.team.TeamDummy;

import java.util.Date;

public class MatchDummy {

    private TeamDummy teamDummy = new TeamDummy();
    private Team team1 = teamDummy.defaultTeam();
    private Team team2 = teamDummy.alternateTeam();
    private Team team3 = teamDummy.alternateTeam2();

    public Match defaultMatch() {
        return new Match(new Date(), new Date(), "Kicker1", team1, team2);
    }

    public Match matchWithNull() {
        return new Match(new Date(), new Date(), "Kicker1", team3, null);
    }

}
