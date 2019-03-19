package de.adesso.kicker.user.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, String> {
    int countAllByRatingAfter(int rating);
}
