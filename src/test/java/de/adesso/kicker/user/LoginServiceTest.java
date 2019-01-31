package de.adesso.kicker.user;

import de.adesso.kicker.role.RoleRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import de.adesso.kicker.login.LoginService;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LoginServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private LoginService loginService;

    private UserDummy userDummy = new UserDummy();

    private User user;
    private User otherUser;

    @BeforeAll
    void setUpAll() {
        setUpMockData();
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findByEmail(any(String.class))).thenReturn(user);
        when(userRepository.findByEmail(eq(otherUser.getEmail()))).thenReturn(null);
    }

    @BeforeEach
    void setUp() {

    }

//    @Test
//    void testSaveUser() {
//        loginService.saveUser(user);
//    }

    @Test
    void findUserByEmail() {
        User emailUser = loginService.findUserByEmail("user-email");
        assertEquals(user, emailUser);
    }

    @Test
    void checkUserExists() {
//        Assertions.assertThrows(UserAlreadyExistsException.class, () -> loginService.checkUserExists(user));
    }

    @Test
    void checkUserExists_Success() {
        loginService.checkUserExists(otherUser);
    }

    private void setUpMockData() {
        this.user = userDummy.defaultUser();
        this.otherUser = userDummy.alternateUser();
    }
}