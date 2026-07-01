package ir.ac.ut.ece.ie.auth;

import ir.ac.ut.ece.ie.users.User;
import ir.ac.ut.ece.ie.users.UserMapper;
import ir.ac.ut.ece.ie.users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;

    public User login(LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );

        return userRepository.findByUsername(request.getUsername()).orElseThrow();
    }

    public User me() {
        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        if (authentication == null) {
            return null;
        }

        Long userId = (Long) authentication.getPrincipal();

        return userRepository.findById(userId)
                .orElse(null);
    }

    public UserDto getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        if (authentication == null) {
            return null;
        }

        var userId = (Long) authentication.getPrincipal();

        return userRepository.findById(userId)
                .map(userMapper::toDto)
                .orElse(null);
    }
}
