package mk.ukim.finki.taskmanagerapp.Web.rest;

import mk.ukim.finki.taskmanagerapp.Model.User;
import mk.ukim.finki.taskmanagerapp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            String email = request.get("email");
            String password = request.get("password");

            User createdUser = userService.registerUser(username, email, password);
            return ResponseEntity.ok(Map.of("message", "User registered successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Username or Email already exists"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest, HttpSession session) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        User user = userService.login(username, password);
        if (user != null) {
            session.setAttribute("user", user);
            session.setAttribute("username", user.getUsername());

            return ResponseEntity.ok(Map.of(
                    "username", user.getUsername(),
                    "email", user.getEmail()
            ));
        } else {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid username or password"));
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }

    @GetMapping("/user")
    public ResponseEntity<?> currentUser(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            User user = userService.findByUsername(username).orElse(null);
            if (user != null) {
                return ResponseEntity.ok(Map.of(
                        "username", user.getUsername(),
                        "email", user.getEmail()
                ));
            } else {

                return ResponseEntity.status(401).body(Map.of("error", "User not found"));
            }
        } else {
            return ResponseEntity.status(401).body(Map.of("error", "Not logged in"));
        }
    }
}
