package ir.ac.ut.ece.ie.users;

import ir.ac.ut.ece.ie.auth.AuthService;
import ir.ac.ut.ece.ie.auth.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final AdminRepository adminRepository;
    private final AuthService authService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserDto getUser(String username) {
        var user = authService.me();

        return userMapper.toDto(user);
    }

    public UserDto addUser(AddUserRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UsernameExistsException();
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailExistsException();
        }

        User user;
        if (request.getRole().equals("customer")) {
            var customer = userMapper.toCustomer(request);
            customer.setPassword(passwordEncoder.encode(request.getPassword()));

            customerRepository.save(customer);
            user = customer;
        }
        else {
            var admin = userMapper.toAdmin(request);
            admin.setPassword(passwordEncoder.encode(request.getPassword()));

            adminRepository.save(admin);
            user = admin;
        }

        return userMapper.toDto(user);
    }

    public User addIfNotExists(String email, String name) {
        var user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            return user;
        }

        String usernamePrefix = name.replace(" ", "_");

        List<String> usernames = userRepository.findUsernamesStartingWith(usernamePrefix);

        String username = usernamePrefix;

        if (usernames.contains(usernamePrefix)) {
            int suffix = 1;
            while (usernames.contains(usernamePrefix + suffix)) {
                suffix++;
            }
            username = usernamePrefix + suffix;
        }

        Customer newUser = new Customer();
        newUser.setUsername(username);
        newUser.setEmail(email);

        return customerRepository.save(newUser);
    }
}
