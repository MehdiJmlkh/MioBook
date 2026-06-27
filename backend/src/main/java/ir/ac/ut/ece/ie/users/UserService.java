package ir.ac.ut.ece.ie.users;

import ir.ac.ut.ece.ie.auth.UserDto;
import ir.ac.ut.ece.ie.common.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final AdminRepository adminRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserDto getUser(String username) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

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
}
