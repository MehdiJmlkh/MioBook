package ir.ac.ut.ece.ie.auth;

import ir.ac.ut.ece.ie.users.User;
import ir.ac.ut.ece.ie.users.UserMapper;
import ir.ac.ut.ece.ie.users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final AuthRepository authRepository;
    private final UserMapper userMapper;

    public UserDto login(LoginRequest request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(UsernameOrPasswordIncorrectException::new);

        if (!user.getPassword().equals(request.getPassword())) {
            throw new UsernameOrPasswordIncorrectException();
        }

        return userMapper.toDto(user);
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

        Long userId = (Long) authentication.getPrincipal();

        return userRepository.findById(userId)
                .map(userMapper::toDto)
                .orElse(null);
    }
}
