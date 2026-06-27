package ir.ac.ut.ece.ie.auth;

import ir.ac.ut.ece.ie.common.ErrorDto;
import ir.ac.ut.ece.ie.sessions.SessionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final SessionService sessionService;

    @GetMapping
    public ResponseEntity<UserDto> getCurrentUser() {
        var userDto = authService.getCurrentUser();
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        authService.login(request);

        String token = sessionService.createSession(1L);

        return new LoginResponse(token);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @RequestHeader("Authorization") String authorization) {

        String token = authorization.substring(7);
        sessionService.deleteSession(token);

        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDto> handleBadCredentialsExceptionException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorDto("Username or password is incorrect."));
    }
}

