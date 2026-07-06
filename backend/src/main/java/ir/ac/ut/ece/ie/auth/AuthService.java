package ir.ac.ut.ece.ie.auth;

import ir.ac.ut.ece.ie.users.*;
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

        var user = (AuthenticatedUser) authentication.getPrincipal();

        return userRepository.findById(user.getId())
                .orElse(null);
    }

    public Customer currentCustomer() {
        User user = me();

        if (!(user instanceof Customer customer)) {
            throw new IllegalStateException("Current user is not a customer");
        }

        return customer;
    }

    public Admin currentAdmin() {
        User user = me();

        if (!(user instanceof  Admin admin)) {
            throw new IllegalStateException("Current user is not an admin");
        }

        return admin;
    }

    public UserDto getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        if (authentication == null) {
            return null;
        }

        var user = (AuthenticatedUser) authentication.getPrincipal();

        return userRepository.findById(user.getId())
                .map(userMapper::toDto)
                .orElse(null);
    }
}
