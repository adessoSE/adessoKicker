package de.adesso.kicker.user.service;

import de.adesso.kicker.user.exception.UserNotFoundException;
import de.adesso.kicker.user.persistence.User;
import de.adesso.kicker.user.persistence.UserDummy;
import de.adesso.kicker.user.persistence.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.representations.AccessToken;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@TestPropertySource("classpath:application-test.properties")
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    private static User createUser() {
        return UserDummy.defaultUser();
    }

    private static List<User> createUserListWithStatistics() {
        return Collections.singletonList(UserDummy.userWithVeryHighRating());
    }

    @Test
    @DisplayName("Should return a list of all users")
    void whenUsersExistReturnAllUsers() {
        // given
        var userList = createUserListWithStatistics();
        given(userRepository.findAll()).willReturn(userList);

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
        given(userRepository.findAll()).willReturn(expected);

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
        given(userRepository.findById(user.getUserId())).willReturn(Optional.of(user));

        // when
        var actualUser = userService.getUserById(user.getUserId());

        // then
        assertEquals(user, actualUser);
    }

    @Test
    @DisplayName("If user with given id doesn't exist throw UserNotFoundException")
    void whenUserWithIdNotFoundThrowUserNotFoundException() {
        // given
        given(userRepository.findById(anyString())).willReturn(Optional.empty());

        // when
        Executable when = () -> userService.getUserById("non-existent-id");

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
        SecurityContextHolder.setContext(securityContext);

        given(securityContext.getAuthentication()).willReturn(authentication);
        given(authentication.getName()).willReturn(expected.getUserId());
        given(userRepository.findById(expected.getUserId())).willReturn(Optional.of(expected));

        // when
        var actual = userService.getLoggedInUser();

        // then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("If there's no entry for the logged in user create and return it")
    void whenLoggedInUserNotFoundCreateUser() {
        // given
        var user = createUser();
        var authEvent = mock(AuthenticationSuccessEvent.class);
        var authentication = mock(Authentication.class);
        var principal = mock(KeycloakPrincipal.class);
        var simpleAccount = mock(SimpleKeycloakAccount.class);
        var keycloakContext = mock(RefreshableKeycloakSecurityContext.class);
        var accessToken = mock(AccessToken.class);

        given(authEvent.getAuthentication()).willReturn(authentication);

        given(authentication.getPrincipal()).willReturn(principal);

        given(principal.getName()).willReturn(user.getUserId());

        given(authentication.getDetails()).willReturn(simpleAccount);
        given(simpleAccount.getKeycloakSecurityContext()).willReturn(keycloakContext);
        given(keycloakContext.getToken()).willReturn(accessToken);

        given(accessToken.getPreferredUsername()).willReturn(user.getUserId());
        given(accessToken.getGivenName()).willReturn(user.getFirstName());
        given(accessToken.getFamilyName()).willReturn(user.getLastName());
        given(accessToken.getEmail()).willReturn(user.getEmail());

        given(userRepository.findById(anyString())).willReturn(Optional.empty());
        given(userRepository.save(user)).willReturn(user);

        // when
        userService.checkFirstLogin(authEvent);

        // then
        then(userRepository).should(times(1)).save(user);
    }

    @Test
    @DisplayName("Expect a list of users")
    void expectListOfUsers() {
        // given
        var userList = createUserListWithStatistics();
        var page = mock(Page.class);
        given(userRepository.findAllByStatisticNotNull(any(Pageable.class))).willReturn(page);
        given(page.getContent()).willReturn(userList);

        // when
        var actualList = userService.getUserPageSortedByRating(0, 10);

        // then
        assertEquals(userList, actualList);
    }
}