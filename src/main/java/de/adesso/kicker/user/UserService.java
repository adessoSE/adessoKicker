package de.adesso.kicker.user;

import java.util.ArrayList;
import java.util.List;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Service that handles "UserService" used in "UserController".
 */

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    /**
     * getAllUsers() returns a list of all users.
     *
     * @return
     */
    public List<User> getAllUsers() {
        List<User> users;
        users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    /**
     * getUserById() returns an unique user identified by it's id.
     *
     * @param id
     * @return
     */
    public User getUserById(String id) {

        return userRepository.findByUserId(id);
    }

    /**
     * getUserByEmail() returns an unique user identified by it's email.
     *
     * @param email
     */
    public User getUserByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    /**
     * getLoggedInUser() returns the current user.
     *
     * @return
     */

    public User getLoggedInUser() {
        KeycloakPrincipal principal = (KeycloakPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = userRepository.findByUserId(principal.getName());
        try {
            checkUserExists(user);
        } catch (UserDoesNotExistException e) {
            createUser();
        }
        return user;
    }

    private void createUser() {
        SimpleKeycloakAccount simpleKeycloakAccount = (SimpleKeycloakAccount) SecurityContextHolder.getContext()
                .getAuthentication().getDetails();
        AccessToken userAccessToken = simpleKeycloakAccount.getKeycloakSecurityContext().getToken();
        String userId = userAccessToken.getPreferredUsername();
        String firstName = userAccessToken.getGivenName();
        String lastName = userAccessToken.getFamilyName();
        String email = userAccessToken.getEmail();
        User user = new User(userId, firstName, lastName, email);
        userRepository.save(user);
    }

    /**
     * saveUser() saves an user object.
     *
     * @param user
     */
    public void saveUser(User user) {

        userRepository.save(user);
    }

    /**
     * deleteUser() deletes an unique user identified by it's id.
     *
     * @param id
     */
    public void deleteUser(String id) {

        userRepository.delete(userRepository.findByUserId(id));
    }

    /**
     * getUserByNameSearchbar is used for the searchbar, accepts a string and it
     * will be validated by this method into two separate strings if there's a space
     * inbetween.
     *
     * @param firstName
     * @param lastName
     * @return
     */
    public List<User> getUserByNameSearchbar(String firstName, String lastName) {
        List<User> users;
        users = new ArrayList<>();
        try {
            if (firstName.contains(" ")) {
                String[] name = firstName.split("\\s+", 2);
                firstName = name[0];
                lastName = name[1];

            }
        } catch (NullPointerException n) {
        }
        userRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(firstName, lastName)
                .forEach(users::add);
        return users;
    }

    private void checkUserExists(User user) {
        if (user == null) {
            throw new UserDoesNotExistException();
        }
    }
}
