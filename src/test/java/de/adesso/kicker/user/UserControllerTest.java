package de.adesso.kicker.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = UserController.class, secure = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private UserDummy userdummy = new UserDummy();

    @MockBean
    private UserService userService;

    @Test
    public void getLoggedInUser() throws Exception {
        User testUser = userdummy.defaultUser();
        when(userService.getLoggedInUser()).thenReturn(testUser);
        mockMvc.perform(get("/users/you")).andExpect(status().isOk())
                .andExpect(model().attribute("user", userdummy.defaultUser()));
    }

    @Test
    public void getUserById() throws Exception {
        User testUser = userdummy.defaultUser();
        when(userService.getUserById(testUser.getUserId())).thenReturn(testUser);
        mockMvc.perform(get("/users/{id}", testUser.getUserId())).andExpect(status().isOk())
                .andExpect(model().attribute("user", testUser));
    }

}
