package mk.ukim.finki.taskmanagerapp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import mk.ukim.finki.taskmanagerapp.Model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
