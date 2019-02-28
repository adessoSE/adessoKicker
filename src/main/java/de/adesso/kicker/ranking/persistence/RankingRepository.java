package de.adesso.kicker.ranking.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankingRepository extends CrudRepository<Ranking, String> {
    int countAllByRatingAfter(int rating);
}
