package de.adesso.kicker.user;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = UserController.class, secure = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void whenUserLoggedInReturnUser() throws Exception {
        // given
        var user = UserDummy.defaultUser();
        when(userService.getLoggedInUser()).thenReturn(user);

        // when
        var result = mockMvc.perform(get("/users/you"));

        // then
        result.andExpect(status().isOk()).andExpect(model().attribute("user", user));
    }

    @Test
    void whenUserExistsReturnUser() throws Exception {
        // given
        var user = UserDummy.defaultUser();
        when(userService.getUserById(user.getUserId())).thenReturn(user);

        // when
        var result = mockMvc.perform(get("/users/{id}", user.getUserId()));

        // then
        result.andExpect(status().isOk()).andExpect(model().attribute("user", user));
    }

}
