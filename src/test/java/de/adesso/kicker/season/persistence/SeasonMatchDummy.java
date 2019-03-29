package de.adesso.kicker.season.persistence;

import de.adesso.kicker.match.persistence.Match;

public class SeasonMatchDummy {
    public static SeasonMatch seasonMatch(Match match) {
        return SeasonMatch.builder()
                .date(match.getDate())
                .winnerTeamA(match.getWinnerTeamA())
                .teamAPlayer1(match.getTeamAPlayer1())
                .teamAPlayer2(match.getTeamAPlayer2())
                .teamBPlayer1(match.getTeamBPlayer1())
                .teamBPlayer2(match.getTeamBPlayer2())
                .build();
    }
}
