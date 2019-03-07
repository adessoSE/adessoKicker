package de.adesso.kicker.notification.persistence;

import de.adesso.kicker.user.persistence.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
public abstract class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long notificationId;

    @OneToOne
    private User sender;
    @OneToOne
    private User receiver;

    private LocalDate sendDate;

    private NotificationType type;

    public Notification(User sender, User receiver) {
        this.sender = sender;
        this.receiver = receiver;
        this.sendDate = LocalDate.now();
    }
}
