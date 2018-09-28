package de.adesso.adessoKicker.repositories;

import de.adesso.adessoKicker.objects.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    /** Finds Role with String role */
    Role findByRole(String role);
}
