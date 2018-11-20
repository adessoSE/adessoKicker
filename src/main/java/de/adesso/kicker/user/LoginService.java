//package de.adesso.kicker.user;
//
//import java.util.Arrays;
//import java.util.HashSet;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import de.adesso.kicker.role.Role;
//import de.adesso.kicker.role.RoleRepository;
//
//@Service
//public class LoginService {
//
//    private UserRepository userRepository;
//    private RoleRepository roleRepository;
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @Autowired
//    public LoginService(UserRepository userRepository, RoleRepository roleRepository,
//            BCryptPasswordEncoder bCryptPasswordEncoder) {
//
//        this.userRepository = userRepository;
//        this.roleRepository = roleRepository;
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//    }
//
//    public User findUserByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }
//
//    /** Saves a User in the User Table with a salt hashed password and a Role */
//    public void registerUser(User user) {
//
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        user.setActive(1);
//        Role userRole = roleRepository.findByRole("ADMIN");
//        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
//        userRepository.save(user);
//    }
//
//    public void checkUserExists(User user) {
//        if (findUserByEmail(user.getEmail()) != null) {
//            throw new UserAlreadyExistsException();
//        }
//    }
//
//}
