package ir.ac.ut.ece.ie.users;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;

@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository = new UserRepository();
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);;

    public Response addUser(@Valid AddUserRequest request) {
        var user = userRepository.findByUsername(request.getUsername()).orElse(null);
        if (user != null) {
            return Response.failed("A user with this username already exists.");
        }

        user = userRepository.findByEmail(request.getEmail()).orElse(null);
        if (user != null) {
            return Response.failed("A user with this email already exists.");
        }

        if (!request.getUsername().matches("^[A-Za-z0-9_-]+$")) {
            return Response.failed("Username is invalid. Only English letters, numbers, -, and _ are allowed.");
        }

        if (!request.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            return Response.failed("Invalid email format");
        }

        if (request.getPassword().length() < 4) {
            return Response.failed("Password must be at least 4 characters long");
        }
        if (!(request.getRole().equals("customer") || request.getRole().equals("admin"))) {
            return Response.failed("Role must be customer or admin.");
        }

        user = userMapper.toUser(request);

        userRepository.addUser(user);

        return Response.ok("User added successfully.");
    }
}
