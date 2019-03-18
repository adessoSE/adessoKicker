package de.adesso.kicker.user.trackedranking.persistence;

import de.adesso.kicker.user.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrackedRankingRepository extends JpaRepository<TrackedRanking, String> {
    List<TrackedRanking> findAllByUser(User user);
}
