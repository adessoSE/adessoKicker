package de.adesso.kicker.user;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    /** Returns a List of Users found with lastName */
    List<User> findByLastName(String lastName);

    /** Returns a single User found with email */
    User findByEmail(String email);

    /** Returns a single User found with id */
    User findByUserId(Long id);

    List<User> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);
}
