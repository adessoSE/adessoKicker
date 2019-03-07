package de.adesso.kicker.notification.persistence;

import de.adesso.kicker.user.persistence.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Long> {
    List<Notification> findAllByReceiver(User receiver);
}
