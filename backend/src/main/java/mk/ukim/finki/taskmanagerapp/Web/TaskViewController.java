package mk.ukim.finki.taskmanagerapp.Web;

import mk.ukim.finki.taskmanagerapp.Model.Task;
import mk.ukim.finki.taskmanagerapp.Service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/tasks")
public class TaskViewController {

    private final TaskService taskService;

    public TaskViewController(TaskService taskService) {
        this.taskService = taskService;
    }


    @GetMapping
    public String getAllTasks(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        model.addAttribute("loggedUser", "TestUser");
        return "tasks";
    }


    @GetMapping("/add")
    public String showAddTaskForm() {
        return "add-task";
    }


    @PostMapping("/add")
    public String addTask(@RequestParam String title,
                          @RequestParam String description,
                          @RequestParam(required = false) String dueDate) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setDueDate(LocalDate.parse(dueDate));

        task.setCompleted(false);

        taskService.save(task);
        return "redirect:/tasks";
    }

    @GetMapping("/{id}/edit")
    public String showEditTaskForm(@PathVariable String id, Model model) {
        Task task = taskService.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        model.addAttribute("task", task);
        return "edit-task";  // name of your edit form HTML
    }

    @PostMapping("/{id}/edit")
    public String editTask(@PathVariable String id,
                           @RequestParam String title,
                           @RequestParam String description,
                           @RequestParam(required = false) String dueDate,
                           @RequestParam(required = false) Boolean completed) {
        Task task = taskService.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setTitle(title);
        task.setDescription(description);
        if (dueDate != null && !dueDate.isEmpty()) {
            task.setDueDate(LocalDate.parse(dueDate));
        } else {
            task.setDueDate(null);
        }
        if (completed != null) {
            task.setCompleted(completed);
        }

        taskService.save(task);
        return "redirect:/tasks";
    }


    @PostMapping("/{id}/complete")
    public String completeTask(@PathVariable String id) {
        taskService.completeTask(id);
        return "redirect:/tasks";
    }


    @PostMapping("/{id}/delete")
    public String deleteTask(@PathVariable String id) {
        taskService.deleteById(id);
        return "redirect:/tasks";
    }
}
