package de.adesso.kicker.notification.matchverificationrequest.persistence;

import de.adesso.kicker.match.persistence.Match;
import de.adesso.kicker.user.persistence.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchVerificationRequestRepository extends CrudRepository<MatchVerificationRequest, Long> {

    List<MatchVerificationRequest> getAllByMatch(Match match);

}
