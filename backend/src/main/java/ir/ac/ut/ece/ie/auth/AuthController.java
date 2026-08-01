package ir.ac.ut.ece.ie.auth;

import com.auth0.jwt.interfaces.Claim;
import ir.ac.ut.ece.ie.common.ErrorDto;
import ir.ac.ut.ece.ie.config.JwtConfig;
import ir.ac.ut.ece.ie.users.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.Map;

import com.auth0.jwt.JWT;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final JwtConfig jwtConfig;
    private final RestClient restClient;

    @Value("${spring.oauth.googleClient.id}")
    private String clientId;

    @Value("${spring.oauth.googleClient.secret}")
    private String clientSecret;

    @Value("${spring.frontend.url}")
    private String frontendUrl;

    @Value("${spring.frontend.authCallbackPath}")
    private String authCallback;

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
    public Map<String, Claim> googleLogin(@RequestBody GoogleAuthRequest request) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("code", request.getCode());
        body.add("grant_type", "authorization_code");
        body.add("redirect_uri", frontendUrl + authCallback);

        var response = restClient.post()
                .uri("https://oauth2.googleapis.com/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(body)
                .retrieve()
                .body(GoogleTokenResponse.class);

//        var verifier = new GoogleIdTokenVerifier.Builder(
//                    new NetHttpTransport(),
//                    GsonFactory.getDefaultInstance()
//                )
//                .setAudience(List.of(clientId))
//                .build();
//
//        var idToken = verifier.verify(response.getIdToken());

        assert response != null;
        var claims = JWT.decode(response.getIdToken()).getClaims();

        var email = claims.get("email").toString();
        System.out.println(email);
        return claims;
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDto> handleBadCredentialsExceptionException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorDto("Username or password is incorrect."));
    }
}

