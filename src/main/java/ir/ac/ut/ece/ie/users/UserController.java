package ir.ac.ut.ece.ie.users;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;


    @PostMapping("/add")
    public ResponseEntity<Response> addUser(@Valid @RequestBody AddUserRequest addUserRequest) {
        var response = userService.addUser(addUserRequest);
        return ResponseEntity.ok(response);
    }
}
