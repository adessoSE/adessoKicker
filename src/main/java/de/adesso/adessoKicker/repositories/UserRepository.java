package de.adesso.adessoKicker.repositories;

import de.adesso.adessoKicker.objects.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Returns a List of Users found with lastName
     */
    List<User> findByLastName(String lastName);

    /**
     * Returns a single User found with email
     */
    User findByEmail(String email);

    /**
     * Returns a single user found with username
     */
    User findByUsername(String username);

    /**
     * Returns a single User found with id
     */
    User findByUserId(Long id);

}
