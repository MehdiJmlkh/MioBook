package ir.ac.ut.ece.ie.auth;

import ir.ac.ut.ece.ie.common.UserNotFoundException;
import ir.ac.ut.ece.ie.users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final AuthRepository authRepository;

    public void login(LoginRequest request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(UserNotFoundException::new);

        var loggedInUser = authRepository.getAuthenticatedUser().orElse(null);

        if (loggedInUser != null && loggedInUser != user) {
            throw new AnotherUserAlreadyLoggedInException();
        }

        authRepository.setAuthenticatedUser(user);
    }
}
