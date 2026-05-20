package ir.ac.ut.ece.ie.auth;

import ir.ac.ut.ece.ie.common.ErrorDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @GetMapping
    public ResponseEntity<UserDto> getCurrentUser() {
        var userDto = authService.getLoggedInUser();
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequest request) {
        var userDto = authService.login(request);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        authService.logout();
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(UsernameOrPasswordIncorrectException.class)
    public ResponseEntity<ErrorDto> handleUsernameOrPasswordIncorrectException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorDto("Username or password is incorrect."));
    }
}

