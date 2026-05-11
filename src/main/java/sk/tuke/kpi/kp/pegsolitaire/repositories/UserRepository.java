package sk.tuke.kpi.kp.pegsolitaire.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.tuke.kpi.kp.pegsolitaire.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
