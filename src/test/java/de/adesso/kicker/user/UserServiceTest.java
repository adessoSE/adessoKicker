package de.adesso.kicker.user;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    private UserDummy userDummy = new UserDummy();

    private User user = userDummy.defaultUser();
    private User otherUser = userDummy.alternateUser();

    @BeforeAll
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    static User createUser() {
        var userDummy = new UserDummy();
        return userDummy.defaultUser();
    }

    static List<User> createUserList() {
        var userDummy = new UserDummy();
        return Collections.singletonList(userDummy.defaultUser());
    }

    @Test
    void testGetAllUsers() {
        // given
        var userList = createUserList();
        when(userRepository.findAll()).thenReturn(userList);

        // when
        var actualList = userService.getAllUsers();

        // then
        assertEquals(userList, actualList);
    }

    @Test
    void testGetUserById_Success() {
        // given
        var user = createUser();
        when(userRepository.findByUserId(user.getUserId())).thenReturn(user);

        // when
        var actualUser = userService.getUserById(user.getUserId());

        // then
        assertEquals(user, actualUser);
    }

    @Test
    void testGetUserByEmail_Success() {
        // given
        var user = createUser();
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        // when
        User actualUser = userService.getUserByEmail(user.getEmail());

        // then
        assertEquals(user, actualUser);
    }
}