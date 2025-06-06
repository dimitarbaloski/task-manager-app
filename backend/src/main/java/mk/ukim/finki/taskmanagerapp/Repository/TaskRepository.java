package mk.ukim.finki.taskmanagerapp.Repository;

import mk.ukim.finki.taskmanagerapp.Model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import mk.ukim.finki.taskmanagerapp.Model.User;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByTitle(String title);
    List<Task> findByUser(User user);

}
