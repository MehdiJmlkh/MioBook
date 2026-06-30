package ir.ac.ut.ece.ie.users;

import ir.ac.ut.ece.ie.common.UserNotFoundException;
import ir.ac.ut.ece.ie.testdata.TestDataFactory;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {
    @MockitoBean
    private UserRepository userRepository;
    @MockitoBean
    private CustomerRepository customerRepository;
    @MockitoBean
    private AdminRepository adminRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void addUser_duplicateUsername_throwsException() {
        var request = new AddUserRequest();
        request.setUsername("username");

        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(new User()));

        assertThrows(UsernameExistsException.class, () -> userService.addUser(request));
    }

    @Test
    void addUser_duplicateEmail_ThrowsException() {
        var request = new AddUserRequest();
        request.setEmail("email@domain.com");

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(new User()));

        assertThrows(EmailExistsException.class, () -> userService.addUser(request));
    }

    @Test
    void addUser_validCustomer_savesCustomer() {
        var request = AddUserRequest.builder()
                .role("customer")
                .username("username")
                .password("1234")
                .email("email@domain.com")
                .address(new AddressDto("country", "city"))
                .build();

        var userDto = userService.addUser(request);

        var captor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).save(captor.capture());
        var savedUser = captor.getValue();

        assertEquals(request.getUsername(), savedUser.getUsername());
        assertTrue(passwordEncoder.matches(request.getPassword(), savedUser.getPassword()));
        assertEquals(request.getEmail(), savedUser.getEmail());
        assertEquals(request.getAddress().getCountry(), savedUser.getAddress().getCountry());
        assertEquals(request.getAddress().getCity(), savedUser.getAddress().getCity());
        assertEquals(Role.CUSTOMER, savedUser.getRole());
        assertNotNull(savedUser.getCart());
        assertNotNull(savedUser.getWallet());

        assertEquals(userDto.getUsername(), request.getUsername());
        assertEquals(userDto.getEmail(), request.getEmail());
        assertEquals(userDto.getRole(), Role.CUSTOMER.toString());
    }

    @Test
    void addUser_validAdmin_savesAdmin() {
        var request = AddUserRequest.builder()
                .role("admin")
                .username("username")
                .password("1234")
                .email("email@domain.com")
                .address(new AddressDto("country", "city"))
                .build();

        var userDto = userService.addUser(request);

        var captor = ArgumentCaptor.forClass(Admin.class);
        verify(adminRepository).save(captor.capture());
        var savedUser = captor.getValue();

        assertEquals(request.getUsername(), savedUser.getUsername());
        assertTrue(passwordEncoder.matches(request.getPassword(), savedUser.getPassword()));
        assertEquals(request.getEmail(), savedUser.getEmail());
        assertEquals(request.getAddress().getCountry(), savedUser.getAddress().getCountry());
        assertEquals(request.getAddress().getCity(), savedUser.getAddress().getCity());
        assertEquals(Role.ADMIN, savedUser.getRole());

        assertEquals(userDto.getUsername(), request.getUsername());
        assertEquals(userDto.getEmail(), request.getEmail());
        assertEquals(userDto.getRole(), Role.ADMIN.toString());
    }

    @Test
    void getUser_userNotFound_throwsException() {
        assertThrows(UserNotFoundException.class, () -> userService.getUser("username"));
    }

    @Test
    void getUser_validCustomer_returnsCustomer() {
        var user = TestDataFactory.sampleCustomerUser();

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        var userDto = userService.getUser(user.getUsername());

        assertEquals(userDto.getUsername(), user.getUsername());
        assertEquals(userDto.getEmail(), user.getEmail());
        assertEquals(userDto.getRole(), Role.CUSTOMER.toString());
    }

    @Test
    void getUser_validAdmin_returnsAdmin() {
        var user = TestDataFactory.sampleAdminUser();

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        var userDto = userService.getUser(user.getUsername());

        assertEquals(userDto.getUsername(), user.getUsername());
        assertEquals(userDto.getEmail(), user.getEmail());
        assertEquals(userDto.getRole(), Role.ADMIN.toString());
    }
}
