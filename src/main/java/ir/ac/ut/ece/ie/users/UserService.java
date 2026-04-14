package ir.ac.ut.ece.ie.users;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Response addUser(AddUserRequest request) {
        var user = userRepository.findByUsername(request.getUsername()).orElse(null);
        if (user != null) {
            return Response.failed("A user with this username already exists.");
        }

        user = userRepository.findByEmail(request.getEmail()).orElse(null);
        if (user != null) {
            return Response.failed("A user with this email already exists.");
        }

        user = userMapper.toUser(request);

        userRepository.addUser(user);

        return Response.ok("User added successfully.");
    }
}
