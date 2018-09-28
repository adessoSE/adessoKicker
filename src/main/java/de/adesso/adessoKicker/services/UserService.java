package de.adesso.adessoKicker.services;

import de.adesso.adessoKicker.objects.User;
import de.adesso.adessoKicker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
}
