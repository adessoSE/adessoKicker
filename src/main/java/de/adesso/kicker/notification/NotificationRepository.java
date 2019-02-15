package de.adesso.kicker.notification;

import org.springframework.data.repository.CrudRepository;
import de.adesso.kicker.user.User;
import java.util.List;

public interface NotificationRepository extends CrudRepository<Notification, Long> {

    Notification findByNotificationId(long id);

    List<Notification> findAllByReceiver(User receiver);
}
