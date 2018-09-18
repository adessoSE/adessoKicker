package de.adesso.adessoKicker.repositories;

import de.adesso.adessoKicker.objects.Match;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MatchRepository extends CrudRepository<Match, Long> {

}
