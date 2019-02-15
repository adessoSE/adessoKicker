package de.adesso.kicker.notification.MatchVerificationRequest;

import de.adesso.kicker.match.Match;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface MatchVerificationRequestRepository extends CrudRepository<MatchVerificationRequest, Long> {

    MatchVerificationRequest findByNotificationId(long id);

    List<MatchVerificationRequest> getAllByMatch(Match match);
}
