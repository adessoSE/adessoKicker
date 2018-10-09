package de.adesso.kicker.role;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    /** Finds Role with String role */
    Role findByRole(String role);
}
