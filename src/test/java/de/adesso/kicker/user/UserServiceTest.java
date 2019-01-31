package de.adesso.kicker.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    private UserDummy userDummy = new UserDummy();

    private User user = userDummy.defaultUser();
    private User otherUser = userDummy.alternateUser();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        when(userRepository.findAll()).thenReturn(Arrays.asList(user, otherUser));

        when(userRepository.findByUserId(anyString())).thenReturn(null);
        when(userRepository.findByUserId(eq(user.getUserId()))).thenReturn(user);

        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(userRepository.findByEmail(eq(user.getEmail()))).thenReturn(user);

        when(userRepository.save(any(User.class))).thenAnswer((Answer<User>) invocation -> {
            Object[] args = invocation.getArguments();
            return (User) args[0];
        });
    }

    @Test
    void testGetAllUsers() {
        ArrayList<User> allUsers = new ArrayList<>(userService.getAllUsers());
        assertTrue(allUsers.contains(user));
    }

    @Test
    void testGetUserById_Success() {
        User idUser = userService.getUserById(user.getUserId());
        assertEquals(idUser, user);
    }

//    @Test
//    void testGetUserById_NotExisting() {
//        Assertions.assertThrows(UserNotFoundException.class, () -> {
//            userService.getUserById(-1);
//        });
//    }

    @Test
    void testGetUserByEmail_Success() {
        User emailUser = userService.getUserByEmail(user.getEmail());
        assertEquals(emailUser, user);
    }

//    @Test
//    void testGetUserByEmail_NotExisting() {
//        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getUserByEmail("not-existing-email"));
//    }

//    @Test
//    void testSaveUser() {
//        User savedUser = userService.saveUser(user);
//        verify(userRepository).save(user);
//        assertEquals(savedUser, user);
//    }

}