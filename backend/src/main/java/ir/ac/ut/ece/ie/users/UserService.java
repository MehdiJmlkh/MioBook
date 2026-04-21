package ir.ac.ut.ece.ie.users;

import ir.ac.ut.ece.ie.common.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
    }

    public User addUser(AddUserRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UsernameExistsException();
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailExistsException();
        }

        var user = userMapper.toUser(request);
        user.setBalance(0);

        userRepository.addUser(user);
        return user;
    }
}
