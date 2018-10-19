package de.adesso.kicker.user;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * findByLastName() returns a list of users by their lastName.
     * 
     * @param lastName
     * @return
     */
    List<User> findByLastName(String lastName);

    /**
     * findByTeamMail finds a user by it's email.
     * 
     * @param email
     * @return
     */
    User findByEmail(String email);

    /**
     * findByUserId() finds a user by it's id.
     * 
     * @param id
     * @return
     */
    User findByUserId(Long id);

    /**
     * findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase() finds
     * user by the same firstName and lastName or only the first/lastname ignoring
     * the case or something similar to it.
     * 
     * @param firstName
     * @param lastName
     * @return
     */
    List<User> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);
}
