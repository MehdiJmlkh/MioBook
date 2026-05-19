package ir.ac.ut.ece.ie.users;

import ir.ac.ut.ece.ie.auth.AuthMapper;
import ir.ac.ut.ece.ie.auth.UserDto;
import ir.ac.ut.ece.ie.common.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final AdminRepository adminRepository;
    private final UserMapper userMapper;
    private final AuthMapper authMapper;

    public User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
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
            customerRepository.addCustomer(customer);
            customer.setBalance(0);
            user = customer;
        }
        else {
            var admin = userMapper.toAdmin(request);
            adminRepository.addAdmin(admin);
            user = admin;
        }

        userRepository.save(user);
        return authMapper.toDto(user);
    }
}
