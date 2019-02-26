package de.adesso.kicker.notification.MatchVerificationRequest;

import de.adesso.kicker.match.Match;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchVerificationRequestRepository extends CrudRepository<MatchVerificationRequest, Long> {

    List<MatchVerificationRequest> getAllByMatch(Match match);
}
