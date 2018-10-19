package de.adesso.kicker.notification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import de.adesso.kicker.notification.Notification.NotificationType;
import de.adesso.kicker.team.Team;
import de.adesso.kicker.team.TeamRepository;
import de.adesso.kicker.user.User;

public class NotificationService {
	
	private NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }
	
    //Gets a notification by id
  	public Notification getNotificationById(long id) {
  		
  		return notificationRepository.findByNotificationId(id);
    }
    
    //Get all notifications
	public List<Notification> getAllNotifications() {
		
		List<Notification> notifications = new ArrayList<>();
		//Add every element of Iterable (because findAll() returns Iterable) to List
        notificationRepository.findAll().forEach(notifications::add);
        return notifications;
    }
	
	 //Get all notifications of a specific type
	public List<Notification> getAllNotificationsByNotificationType(NotificationType notificationType) {
		
		List<Notification> notifications = new ArrayList<>();
		//Add every element of Iterable (because findAll() returns Iterable) to List
        notificationRepository.findByNotificationType(notificationType).forEach(notifications::add);
        return notifications;
	}
	
	 //Get all notifications that were send by a specific user
	public List<Notification> getAllNotificationsBySender(User sender) {
		
		List<Notification> notifications = new ArrayList<>();
		//Add every element of Iterable (because findAll() returns Iterable) to List
        notificationRepository.findBySender(sender).forEach(notifications::add);
        return notifications;
	}
	
	//Get all notifications that were received by a specific user
	public List<Notification> getAllNotificationsByReceiver(User receiver) {
		
		List<Notification> notifications = new ArrayList<>();
		//Add every element of Iterable (because findAll() returns Iterable) to List
        notificationRepository.findByReceiver(receiver).forEach(notifications::add);
        return notifications;
	}
}
