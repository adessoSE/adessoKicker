package de.adesso.kicker.notification;

import de.adesso.kicker.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Long> {

    List<Notification> findAllByReceiver(User receiver);
}
