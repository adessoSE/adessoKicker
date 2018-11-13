package de.adesso.kicker.notification;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.adesso.kicker.user.UserService;

public class NotificationServiceTest {

    @Mock
    private UserService userService;
    
    @InjectMocks
    private NotificationService notificationService;
    
    @Mock
    private NotificationRepository notificationRepository;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void Create_Notification_By_Passing_Params_Should_Work() {
        
        notificationService.saveNotification("Test", 43L, 43L);
    }
}
