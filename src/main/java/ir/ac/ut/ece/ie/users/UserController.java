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

    @PostMapping("/add")
    public ResponseEntity<Void> addUser(@Valid @RequestBody AddUserRequest addUserRequest) {
        userService.addUser(addUserRequest);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(UsernameExistsException.class)
    public ResponseEntity<ErrorDto> handleUsernameExistsException() {
        return ResponseEntity.badRequest().body(new ErrorDto("A user with this username already exists."));
    }

    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<ErrorDto> handleEmailExistsException() {
        return ResponseEntity.badRequest().body(new ErrorDto("A user with this email already exists."));
    }
}
