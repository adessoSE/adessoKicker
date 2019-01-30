package de.adesso.kicker.notification.matchverificationrequest;

import de.adesso.kicker.match.Match;
import org.springframework.data.repository.CrudRepository;

public interface MatchVerificationRequestRepository extends CrudRepository<MatchVerificationRequest, Long> {

    MatchVerificationRequest findByNotificationId(long notificationId);

    MatchVerificationRequest findByMatch(Match match);

    void deleteByMatch(Match match);
}
