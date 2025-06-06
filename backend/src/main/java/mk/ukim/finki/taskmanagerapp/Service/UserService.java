package mk.ukim.finki.taskmanagerapp.Service;

import mk.ukim.finki.taskmanagerapp.Model.Task;
import mk.ukim.finki.taskmanagerapp.Model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    List<User> findAll();
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    User save(User user);
    void deleteById(Long id);
    User registerUser(String username, String email, String rawPassword);
    User login(String username, String password);

}
