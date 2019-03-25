package de.adesso.kicker.match.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MatchRepository extends CrudRepository<Match, String> {
    List<Match> findAllByVerified(boolean verified);
}
