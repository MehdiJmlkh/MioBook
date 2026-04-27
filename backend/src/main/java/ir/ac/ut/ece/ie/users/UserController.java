package ir.ac.ut.ece.ie.users;


import ir.ac.ut.ece.ie.common.ErrorDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{username}")
    public User getUser(@PathVariable String username) {
        return userService.getUser(username);
    }

    @PostMapping
    public ResponseEntity<User> addUser(@Valid @RequestBody AddUserRequest addUserRequest) {
        var user = userService.addUser(addUserRequest);
        return ResponseEntity.ok(user);
    }

    @ExceptionHandler(UsernameExistsException.class)
    public ResponseEntity<Map<String, String>> handleUsernameExistsException() {
        return ResponseEntity.badRequest().body(Map.of("username", "Username is already registered."));
    }

    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<Map<String, String>> handleEmailExistsException() {
        return ResponseEntity.badRequest().body(Map.of("email", "Email is already registered."));
    }
}
