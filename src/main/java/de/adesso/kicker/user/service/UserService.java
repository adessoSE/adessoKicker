package de.adesso.kicker.user.service;

import de.adesso.kicker.ranking.persistence.Ranking;
import de.adesso.kicker.user.exception.UserNotFoundException;
import de.adesso.kicker.user.persistence.User;
import de.adesso.kicker.user.persistence.UserRepository;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        var users = new ArrayList<User>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    public User getLoggedInUser() {
        var principal = getPrincipal();
        return getUserById(principal.getName());
    }

    private KeycloakPrincipal getPrincipal() {
        return (KeycloakPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private void createUser(Authentication authentication) {
        var userAccessToken = getAccessToken(authentication);
        var userId = userAccessToken.getPreferredUsername();
        var firstName = userAccessToken.getGivenName();
        var lastName = userAccessToken.getFamilyName();
        var email = userAccessToken.getEmail();
        User user = new User(userId, firstName, lastName, email, new Ranking());
        saveUser(user);
    }

    private AccessToken getAccessToken(Authentication authentication) {
        var simpleKeycloakAccount = (SimpleKeycloakAccount) authentication.getDetails();
        return simpleKeycloakAccount.getKeycloakSecurityContext().getToken();
    }

    @EventListener
    public void checkFirstLogin(AuthenticationSuccessEvent event) {
        var authentication = event.getAuthentication();
        var principal = (KeycloakPrincipal) authentication.getPrincipal();
        if (!checkUserExists(principal.getName())) {
            createUser(authentication);
        }
    }

    private void saveUser(User user) {
        userRepository.save(user);
    }

    private boolean checkUserExists(String id) {
        return userRepository.existsById(id);
    }
}
