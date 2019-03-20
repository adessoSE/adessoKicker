package de.adesso.kicker.user.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Page<User> findAllByStatisticNotNull(Pageable pageable);

    List<User> findAllByStatisticNotNull();
}
