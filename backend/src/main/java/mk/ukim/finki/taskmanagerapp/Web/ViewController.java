package mk.ukim.finki.taskmanagerapp.Web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping({"/", "/login", "/dashboard", "/register"})
    public String index() {
        return "forward:/index.html";
    }
}

