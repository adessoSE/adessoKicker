package de.adesso.kicker.user;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    public User getUserById(long id) {

        return userRepository.findByUserId(id);
    }

    public User getUserByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    public User getLoggedInUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return getUserByEmail(email);
    }

    public void saveUser(User user) {

        userRepository.save(user);
    }

    public void deleteUser(long id) {

        userRepository.delete(userRepository.findByUserId(id));
    }

    public List<User> getUserByName(String firstName, String lastName) {
        List<User> users = new ArrayList<>();
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
