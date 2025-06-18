package mk.ukim.finki.taskmanagerapp.Service;

import mk.ukim.finki.taskmanagerapp.Model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> findAll();
    Optional<Task> findById(String id);
    Task save(Task task);
    void deleteById(String id);
    Task completeTask(String taskId);

}
