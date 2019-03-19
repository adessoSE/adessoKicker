package de.adesso.kicker.user.trackedstatistics.persistence;

import de.adesso.kicker.user.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrackedStatisticsRepository extends JpaRepository<TrackedStatistics, String> {
    List<TrackedStatistics> findAllByUser(User user);
}
