package de.adesso.kicker.notification;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import de.adesso.kicker.notification.teamjoinrequest.TeamJoinRequest;
import de.adesso.kicker.user.User;

public interface NotificationRepository extends CrudRepository<Notification, Long> {

    // Get a Notification by a specific id
    Notification findByNotificationId(Long id);

    // Get all Notifications that were send from a specific user
    List<Notification> findBySender(User sender);

    // Get all Notifications that were received by a specific user
    List<Notification> findByReceiver(User sender);
}
