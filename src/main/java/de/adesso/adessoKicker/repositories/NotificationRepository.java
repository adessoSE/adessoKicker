package de.adesso.adessoKicker.repositories;

import de.adesso.adessoKicker.objects.Notification;
import org.springframework.data.repository.CrudRepository;

public interface NotificationRepository extends CrudRepository<Notification, Long> {
}
