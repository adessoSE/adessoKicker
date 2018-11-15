package de.adesso.kicker.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * Service that handles "UserService" used in "UserController".
 */

@Service
public class UserService {

    private UserRepository userRepository;
    List<User> users;

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
    public User getUserById(long id) {

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

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return getUserByEmail(email);
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
    public void deleteUser(long id) {

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
}
