package de.adesso.kicker.user.service;

import de.adesso.kicker.ranking.persistence.Ranking;
import de.adesso.kicker.user.exception.UserNotFoundException;
import de.adesso.kicker.user.persistence.User;
import de.adesso.kicker.user.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.representations.AccessToken;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return new ArrayList<>(userRepository.findAll());
    }

    public List<User> getUserPageSortedByRating(int page, int size) {
        var pageable = PageRequest.of(page, size, Sort.by("ranking.rating").descending());
        return userRepository.findAll(pageable).getContent();
    }

    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public User getLoggedInUser() {
        return getUserById(visitingUserId());
    }

    private String visitingUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
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
