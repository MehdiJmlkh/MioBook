package ir.ac.ut.ece.ie.auth;

import ir.ac.ut.ece.ie.common.ErrorDto;
import ir.ac.ut.ece.ie.config.JwtConfig;
import ir.ac.ut.ece.ie.users.UserRepository;
import ir.ac.ut.ece.ie.users.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;


import com.auth0.jwt.JWT;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final JwtConfig jwtConfig;
    private final GoogleAuthService googleAuthService;

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

        var cookie = createRefreshTokenCookie(refreshToken);
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
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        var cookie = new Cookie("refreshToken", "");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/auth/refresh");
        cookie.setMaxAge(0);

        response.addCookie(cookie);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/google")
    public ResponseEntity<JwtResponse> googleLogin(@RequestBody GoogleAuthRequest request,
                            HttpServletResponse response) {
        var idToken = googleAuthService.getIdToken(request.getCode());

        var user = userService.addIfNotExists(idToken.getEmail(), idToken.getName());

        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        var cookie = createRefreshTokenCookie(refreshToken);
        response.addCookie(cookie);

        return ResponseEntity.ok(new JwtResponse(accessToken.toString()));
    }

    private @NonNull Cookie createRefreshTokenCookie(Jwt refreshToken) {
        var cookie = new Cookie("refreshToken", refreshToken.toString());
        cookie.setHttpOnly(true);
        cookie.setPath("/auth/refresh");
        cookie.setMaxAge(jwtConfig.getRefreshTokenExpiration());
        cookie.setSecure(true);
        return cookie;
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDto> handleBadCredentialsExceptionException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorDto("Username or password is incorrect."));
    }

    @ExceptionHandler(GoogleLoginFailedException.class)
    public ResponseEntity<ErrorDto> handleGoogleLoginFailedException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorDto("Google login failed."));
    }
}

