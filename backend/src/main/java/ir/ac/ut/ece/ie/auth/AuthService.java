package ir.ac.ut.ece.ie.auth;

import ir.ac.ut.ece.ie.users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final AuthRepository authRepository;
    private final AuthMapper authMapper;

    public void login(LoginRequest request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(UsernameOrPasswordIncorrectException::new);

        if (!user.getPassword().equals(request.getPassword())) {
            throw new UsernameOrPasswordIncorrectException();
        }

        var loggedInUser = authRepository.getAuthenticatedUser().orElse(null);

        if (loggedInUser != null && loggedInUser != user) {
            throw new AnotherUserAlreadyLoggedInException();
        }

        authRepository.setAuthenticatedUser(user);
    }

    public void logout() {
        authRepository.getAuthenticatedUser()
                .orElseThrow(NotLoggedInException::new);

        authRepository.removeAuthenticatedUser();
    }

    public UserDto getLoggedInUser() {
        var user = authRepository.getAuthenticatedUser()
                .orElse(null);

        if (user == null) {
            return null;
        }

        return authMapper.toDto(user);
    }
}
