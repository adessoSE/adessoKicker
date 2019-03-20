package de.adesso.kicker.user.trackedstatistic.persistence;

import de.adesso.kicker.user.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrackedStatisticRepository extends JpaRepository<TrackedStatistic, String> {
    List<TrackedStatistic> findAllByUser(User user);
}
