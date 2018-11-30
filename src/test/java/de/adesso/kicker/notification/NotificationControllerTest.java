package de.adesso.kicker.notification;

import de.adesso.kicker.notification.teamjoinrequest.TeamJoinRequestService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class NotificationControllerTest{

    @InjectMocks
    NotificationController notificationController;

    @Mock
    NotificationService notificationService;
    @Mock
    TeamJoinRequestService teamJoinRequestService;

    @Before
    public void setUp(){

        MockitoAnnotations.initMocks(this);
    }

    //Test --> Try adding a notification (type = Notification)
    @Test
    public void addNotification_Valid_Input_Should_Work(){

        notificationController.addNotifcationStandard(1L, 1L, "message");
    }
    @Test
    public void addNotification_Message_Null_Should_Work(){

        notificationController.addNotifcationStandard(1L, 1L, null);
    }
    @Test(expected=NullPointerException.class)
    public void addNotification_Sender_Null_Should_Not_Work(){

        notificationController.addNotifcationStandard(null, 1L, "message");
    }
    @Test(expected=NullPointerException.class)
    public void addNotification_Receiver_Null_Should_Not_Work(){

        notificationController.addNotifcationStandard(1L, null, "message");
    }
    @Test(expected=NullPointerException.class)
    public void addNotification_Receiver_And_Sender_Null_Should_Not_Work(){

        notificationController.addNotifcationStandard(null, null, "message");
    }
}
