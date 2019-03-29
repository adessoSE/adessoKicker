package de.adesso.kicker.notification.matchverificationrequest.persistence;

import de.adesso.kicker.match.persistence.MatchDummy;
import de.adesso.kicker.user.persistence.UserDummy;

public class MatchVerificationRequestDummy {

    public static MatchVerificationRequest matchVerificationRequest() {
        return new MatchVerificationRequest(UserDummy.defaultUser(), UserDummy.alternateUser(), MatchDummy.match());
    }
}
