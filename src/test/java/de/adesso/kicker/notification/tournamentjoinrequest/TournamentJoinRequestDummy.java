package de.adesso.kicker.notification.tournamentjoinrequest;

import de.adesso.kicker.team.TeamDummy;
import de.adesso.kicker.tournament.singleelimination.SingleElimDummy;
import de.adesso.kicker.user.UserDummy;

class TournamentJoinRequestDummy {

    private UserDummy userDummy = new UserDummy();
    private TeamDummy teamDummy = new TeamDummy();
    private SingleElimDummy singleElimDummy = new SingleElimDummy();

    TournamentJoinRequest defaultTournamentJoinRequest = new TournamentJoinRequest(userDummy.defaultUser(),
            userDummy.alternateUser(), teamDummy.defaultTeam(), singleElimDummy.defaultSingleElim());
}
