package de.adesso.kicker.notification.matchcreationrequest;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MatchCreationRequestRepository extends CrudRepository<MatchCreationRequest, Long> {

    List<MatchCreationRequest> getAllByMatchCreationValidation(MatchCreationValidation matchCreationValidation);

    MatchCreationRequest findByNotificationId(Long notificationId);
}
