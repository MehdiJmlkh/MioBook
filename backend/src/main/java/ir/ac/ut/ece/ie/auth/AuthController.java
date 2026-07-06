package ir.ac.ut.ece.ie.auth;

import ir.ac.ut.ece.ie.common.ErrorDto;
import ir.ac.ut.ece.ie.config.JwtConfig;
import ir.ac.ut.ece.ie.users.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final JwtConfig jwtConfig;

    @GetMapping
    public ResponseEntity<UserDto> getCurrentUser() {
        var userDto = authService.getCurrentUser();
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(
            @RequestBody LoginRequest request,
            HttpServletResponse response) {
        var user = authService.login(request);

        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        var cookie = new Cookie("refreshToken", refreshToken.toString());
        cookie.setHttpOnly(true);
        cookie.setPath("/auth/refresh");
        cookie.setMaxAge(jwtConfig.getRefreshTokenExpiration());
        cookie.setSecure(true);
        response.addCookie(cookie);

        return ResponseEntity.ok(new JwtResponse(accessToken.toString()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(
            @CookieValue(value = "refreshToken") String refreshToken
    ) {
        var jwt = jwtService.parse(refreshToken);

        if (jwt.isExpired()) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var user = userRepository.findById(jwt.getUserId()).orElseThrow();
        var accessToken = jwtService.generateAccessToken(user);

        return ResponseEntity.ok(new JwtResponse(accessToken.toString()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDto> handleBadCredentialsExceptionException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorDto("Username or password is incorrect."));
    }
}

