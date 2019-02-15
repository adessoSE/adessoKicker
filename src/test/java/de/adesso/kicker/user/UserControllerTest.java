package de.adesso.kicker.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest {


    private MockMvc mockMvc;

    private UserDummy userdummy = new UserDummy();

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Before
    public void setUp() throws Exception{
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testGetUserYourself() throws Exception {
        when(userService.getLoggedInUser()).thenReturn(userdummy.defaultUser());
        mockMvc.perform(get("/users/you")).andExpect(status().isOk()).andExpect(model().attribute("user", userdummy.defaultUser()));
    }

    @Test
    public void testGetUser() throws Exception {
        when(userService.getUserById(userdummy.defaultUser().getUserId())).thenReturn(userdummy.defaultUser());
        mockMvc.perform(get("/users/{id}", userdummy.defaultUser().getUserId())).andExpect(status().isOk()).andExpect(model().attribute("user", userdummy.defaultUser()));
    }

}
