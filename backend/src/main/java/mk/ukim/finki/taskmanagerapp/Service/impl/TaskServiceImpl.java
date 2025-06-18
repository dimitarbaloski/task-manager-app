package mk.ukim.finki.taskmanagerapp.Service.impl;

import mk.ukim.finki.taskmanagerapp.Model.Task;
import mk.ukim.finki.taskmanagerapp.Repository.TaskRepository;
import mk.ukim.finki.taskmanagerapp.Service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Optional<Task> findById(String id) {
        return taskRepository.findById(id);
    }

    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public void deleteById(String id) {
        taskRepository.deleteById(id);
    }


    @Override
    public Task completeTask(String taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setCompleted(true);
        return taskRepository.save(task);
    }

}
