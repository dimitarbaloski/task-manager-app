package mk.ukim.finki.taskmanagerapp.Web.rest;

import mk.ukim.finki.taskmanagerapp.Model.Task;
import mk.ukim.finki.taskmanagerapp.Model.User;
import mk.ukim.finki.taskmanagerapp.Service.TaskService;
import mk.ukim.finki.taskmanagerapp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getTasks(HttpSession session) {
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Not logged in"));
        }

        User user = userService.findByUsername(sessionUser.getUsername()).orElse(null);
        if (user == null) {
            return ResponseEntity.status(401).body(Map.of("error", "User not found"));
        }

        List<Task> tasks = taskService.findByUser(user);
        return ResponseEntity.ok(tasks);
    }


    @PostMapping("/add")
    public ResponseEntity<?> addTask(@RequestBody Task task, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return ResponseEntity.status(401).body(Map.of("error", "Not logged in"));
            }
            task.setUser(user);
            Task savedTask = taskService.save(task);
            return ResponseEntity.ok(savedTask);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Internal server error: " + e.getMessage()));
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<?> editTask(@RequestBody Task task, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return ResponseEntity.status(401).body(Map.of("error", "Not logged in"));
            }
            Task existingTask = taskService.findById(task.getId()).orElse(null);
            if (existingTask == null || !existingTask.getUser().getId().equals(user.getId())) {
                return ResponseEntity.status(404).body(Map.of("error", "Task not found or not authorized"));
            }
            existingTask.setTitle(task.getTitle());
            existingTask.setDescription(task.getDescription());
            existingTask.setDueDate(task.getDueDate());
            existingTask.setCompleted(task.isCompleted());

            Task savedTask = taskService.save(existingTask);
            return ResponseEntity.ok(savedTask);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Internal server error: " + e.getMessage()));
        }
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<?> completeTask(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Not logged in"));
        }

        Task task = taskService.findById(id).orElse(null);
        if (task == null || !task.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(404).body(Map.of("error", "Task not found or not authorized"));
        }

        Task completedTask = taskService.completeTask(id);
        return ResponseEntity.ok(completedTask);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteTask(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Not logged in"));
        }

        Task task = taskService.findById(id).orElse(null);
        if (task == null || !task.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(404).body(Map.of("error", "Task not found or not authorized"));
        }

        taskService.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Task deleted"));
    }
}
