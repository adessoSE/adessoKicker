package de.adesso.kicker.user;

import de.adesso.kicker.user.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.function.Executable;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.representations.AccessToken;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

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
    @DisplayName("Should return a list of all users")
    void whenUsersExistReturnAllUsers() {
        // given
        var userList = createUserList();
        when(userRepository.findAll()).thenReturn(userList);

        // when
        var actualList = userService.getAllUsers();

        // then
        assertEquals(userList, actualList);
    }

    @Test
    @DisplayName("Should return an empty list if no user exists")
    void whenNoUsersExistReturnEmptyList() {
        // given
        List<User> expected = Collections.emptyList();
        when(userRepository.findAll()).thenReturn(expected);

        // when
        var actual = userService.getAllUsers();

        // then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should return a user with given id")
    void whenUserWithIdExistsReturnUser() {
        // given
        var user = createUser();
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));

        // when
        var actualUser = userService.getUserById(user.getUserId());

        // then
        assertEquals(user, actualUser);
    }

    @Test
    @DisplayName("If user with given id doesn't exist throw UserNotFoundException")
    void whenUserWithIdNotFoundThrowUserNotFoundException() {
        // given
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        // when
        Executable when = () -> userService.getUserById("non-existent-id");

        // then
        assertThrows(UserNotFoundException.class, when);
    }

    @Test
    @DisplayName("Should return a user with given email")
    void whenUserWithEmailExistsReturnUser() {
        // given
        var user = createUser();
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        // when
        User actualUser = userService.getUserByEmail(user.getEmail());

        // then
        assertEquals(user, actualUser);
    }

    @Test
    @DisplayName("If user with given email doesn't exist throw UserNotFoundException")
    void whenUserWithEmailNotFoundThrowUserNotFoundException() {
        // given
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // when
        Executable when = () -> userService.getUserByEmail("non-existent-email");

        // then
        assertThrows(UserNotFoundException.class, when);
    }

    @Test
    @DisplayName("If there's an entry for the logged in user return them")
    void whenLoggedInUserExistsReturnUser() {
        // given
        var expected = createUser();
        var authentication = mock(Authentication.class);
        var securityContext = mock(SecurityContext.class);
        var principal = mock(KeycloakPrincipal.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(principal);
        when(principal.getName()).thenReturn(expected.getUserId());
        when(userRepository.findById(anyString())).thenReturn(Optional.of(expected));

        // when
        var actual = userService.getLoggedInUser();

        // then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("If there's no entry for the logged in user create and return it")
    void whenLoggedInUserNotFoundCreateUser() {
        // given
        var expected = createUser();
        var authentication = mock(Authentication.class);
        var securityContext = mock(SecurityContext.class);
        var principal = mock(KeycloakPrincipal.class);
        var simpleAccount = mock(SimpleKeycloakAccount.class);
        var keycloakContext = mock(RefreshableKeycloakSecurityContext.class);
        var accessToken = mock(AccessToken.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(principal);

        when(principal.getName()).thenReturn(expected.getUserId());

        when(authentication.getDetails()).thenReturn(simpleAccount);
        when(simpleAccount.getKeycloakSecurityContext()).thenReturn(keycloakContext);
        when(keycloakContext.getToken()).thenReturn(accessToken);

        when(accessToken.getPreferredUsername()).thenReturn(expected.getUserId());
        when(accessToken.getGivenName()).thenReturn(expected.getFirstName());
        when(accessToken.getFamilyName()).thenReturn(expected.getLastName());
        when(accessToken.getEmail()).thenReturn(expected.getEmail());

        when(userRepository.findById(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(expected)).thenReturn(expected);

        // when
        var actual = userService.getLoggedInUser();

        // then
        assertEquals(expected, actual);
        verify(userRepository, times(1)).save(expected);
    }
}