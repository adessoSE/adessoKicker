package de.adesso.kicker.notification.Message;

import de.adesso.kicker.user.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {

    List<Message> findAllByReceiver(User receiver);
}
