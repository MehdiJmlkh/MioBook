package ir.ac.ut.ece.ie.auth;

import ir.ac.ut.ece.ie.users.UserMapper;
import ir.ac.ut.ece.ie.users.UserRepository;
import lombok.AllArgsConstructor;
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

        authRepository.getAuthenticatedUser().ifPresent(loggedInUser -> {
            if (loggedInUser != user) {
                throw new AnotherUserAlreadyLoggedInException();
            }
        });

        authRepository.setAuthenticatedUser(user);

        return userMapper.toDto(user);
    }

    public void logout() {
        authRepository.getAuthenticatedUser()
                .orElseThrow(NotLoggedInException::new);

        authRepository.removeAuthenticatedUser();
    }

    public UserDto getLoggedInUser() {
         var userId = (Long) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();

        var user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return null;
        }

        return userMapper.toDto(user);
    }
}
