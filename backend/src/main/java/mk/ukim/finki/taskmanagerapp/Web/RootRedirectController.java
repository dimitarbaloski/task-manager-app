package mk.ukim.finki.taskmanagerapp.Web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootRedirectController {

    @GetMapping("/")
    public String redirectToTasks() {
        return "redirect:/tasks";
    }
}
