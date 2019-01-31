package de.adesso.kicker.notification.matchcreationrequest;

import org.springframework.data.repository.CrudRepository;

public interface MatchCreationValidationRepository extends CrudRepository<MatchCreationValidation, Long> {

    MatchCreationValidation getMatchCreationValidationById(Long id);
}
