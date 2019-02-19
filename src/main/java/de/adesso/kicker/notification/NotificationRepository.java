package de.adesso.kicker.notification;

import org.springframework.data.repository.CrudRepository;
import de.adesso.kicker.user.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Long> {

    Notification findByNotificationId(long id);

    List<Notification> findAllByReceiver(User receiver);
}
