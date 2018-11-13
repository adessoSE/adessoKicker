package de.adesso.kicker.notification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.adesso.kicker.user.User;
import de.adesso.kicker.user.UserService;

@Service
public class NotificationService {
	
	private NotificationRepository notificationRepository;
	private UserService userService;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, UserService userService) {

        this.notificationRepository = notificationRepository;
        this.userService = userService;
    }
	
    //Gets a notification by id
  	public Notification getNotificationById(long id) {
  		
  		return notificationRepository.findByNotificationId(id);
    }
    
    //Get all notifications
	public List<Notification> getAllNotifications() {
		
		List<Notification> notifications = new ArrayList<>();
        notificationRepository.findAll().forEach(notifications::add);
        return notifications;
    }
	
	 //Get all notifications that were send by a specific user
	public List<Notification> getAllNotificationsBySender(User sender) {
		
		List<Notification> notifications = new ArrayList<>();
        notificationRepository.findBySender(sender).forEach(notifications::add);
        return notifications;
	}
	
	//Get all notifications that were received by a specific user
	public List<Notification> getAllNotificationsByReceiver(User receiver) {
		
		List<Notification> notifications = new ArrayList<>();
        notificationRepository.findByReceiver(receiver).forEach(notifications::add);
        return notifications;
	}
	
	//Save notification in repository
	public void saveNotification(Notification notification) {
	    notificationRepository.save(notification);
	}

	//Create a notification and save notification in repository
	//!!!!!! NO VALIDATION YET !!!!!!!
	public Notification saveNotification(String message, long receiverId, long senderId) {

	    System.out.println(senderId);
	    User sender = userService.getUserById(senderId);
        User receiver = userService.getUserById(receiverId);
        Notification notification = new Notification(message, receiver, sender);
        saveNotification(notification);
        return notification;
	}

	//Removes notification from repository
	public void removeNotificationById(long id) {

	    notificationRepository.deleteById(id);
	}
}
