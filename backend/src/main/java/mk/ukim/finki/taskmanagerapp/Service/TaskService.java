package mk.ukim.finki.taskmanagerapp.Service;

import mk.ukim.finki.taskmanagerapp.Model.Task;
import mk.ukim.finki.taskmanagerapp.Model.User;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> findAll();
    Optional<Task> findById(Long id);
    Task save(Task task);
    void deleteById(Long id);
    List<Task> findByUser(User user);
    Task completeTask(Long taskId);

}
