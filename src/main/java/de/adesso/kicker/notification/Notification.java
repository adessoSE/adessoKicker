package de.adesso.kicker.notification;

import de.adesso.kicker.user.User;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long notificationId;

    //Notification receiver and sender -> One user
    @ManyToOne(targetEntity = User.class)
    private User receiver;
    
    @ManyToOne(targetEntity = User.class)
    private User sender;
    
    /*	Defines the action executed, if a user interacts with notification
       	Also the message can be based on the NotificationType (except "Standard")
    */
    public enum NotificationType {
    	Standard, 
    	TeamJoinRequest, 
    	TournamentJoinRequest, 
    	TeamAndTournamentJoinRequest, 
    	MatchResultConfirmation}
    ;
    private NotificationType notificationType;
    
    //Date gets defined that moment when creating the notification
    private Date sendDate;

    private String message;

    public Notification() {
    }

    //Fully customizable notification (For debugging or special notifications)
    public Notification(NotificationType notificationType, String message, Date sendDate, User receiver, User sender) {
    	
        this.notificationType = notificationType;
        this.message = message;
        this.sendDate = sendDate;
        this.receiver = receiver;
        this.sender = sender;
    }
    
    //For standard notifications (because they require a custom text)
    public Notification(String message, User receiver, User sender) {
    	
        this.notificationType = NotificationType.Standard;
        this.message = message;
        //Get date at initialization
        this.sendDate = new Date();
        this.receiver = receiver;
        this.sender = sender;
    }
    
	//Default constructor for every other NotificationType (Standard can still be send here but without a custom text)
    public Notification(NotificationType notificationType, User receiver, User sender) {
    	
        this.notificationType = notificationType;
        //Get type based on notificationType
        this.message = getMessageBasedOnNotificationType(notificationType);
        //Get date at initialization
        this.sendDate = new Date();
        this.receiver = receiver;
        this.sender = sender;
    }
    
    //Returns String (message) that is needed for a specific NotificationType
    public String getMessageBasedOnNotificationType(NotificationType notificationType) {
    	
    	if (notificationType == NotificationType.Standard) {
    		
    		return "Standard";
    	}
    	else if (notificationType == NotificationType.TeamJoinRequest) {
    		
    		return "TeamJoinRequest";
    	}
    	else if (notificationType == NotificationType.TournamentJoinRequest) {
    		
    		return "TournamentJoinRequest";
    	}
    	else if (notificationType == NotificationType.TeamAndTournamentJoinRequest) {
    		
    		return "TeamAndTournamentJoinRequest";
    	}
    	else if (notificationType == NotificationType.MatchResultConfirmation) {
    		
    		return "MatchResultConfirmation";
    	}	
    	return "ERROR";
    }
    
    public long getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(long notificationId) {
		this.notificationId = notificationId;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public NotificationType getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(NotificationType notificationType) {
		this.notificationType = notificationType;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

    @Override
    public String toString() {
        return "Notification{" + "notificationId=" + notificationId + ", notificationType=" + notificationType.toString() +
        		", receiver=" + receiver + ", sender=" + sender + ", sendDate=" + sendDate + ", message=" + message;
                
    }
}
