package de.adesso.kicker.user;

import java.util.ArrayList;
import java.util.List;

import de.adesso.kicker.user.exception.UserNotFoundException;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        List<User> users;
        users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    public User getUserById(String id) {

        var user = userRepository.findByUserId(id);
        checkUserExists(user);
        return user;
    }

    public User getUserByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    public User getLoggedInUser() {
        KeycloakPrincipal principal = (KeycloakPrincipal) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        User user = userRepository.findByUserId(principal.getName());
        try {
            checkUserExists(user);
        } catch (UserNotFoundException e) {
            user = createUser();
        }
        return user;
    }

    private User createUser() {
        SimpleKeycloakAccount simpleKeycloakAccount = (SimpleKeycloakAccount) SecurityContextHolder.getContext()
                .getAuthentication()
                .getDetails();
        AccessToken userAccessToken = simpleKeycloakAccount.getKeycloakSecurityContext().getToken();
        String userId = userAccessToken.getPreferredUsername();
        String firstName = userAccessToken.getGivenName();
        String lastName = userAccessToken.getFamilyName();
        String email = userAccessToken.getEmail();
        User user = new User(userId, firstName, lastName, email);
        return userRepository.save(user);
    }

    public void saveUser(User user) {

        userRepository.save(user);
    }

    public void deleteUser(String id) {

        userRepository.delete(userRepository.findByUserId(id));
    }

    private void checkUserExists(User user) {
        if (user == null) {
            throw new UserNotFoundException();
        }
    }
}
