package ir.ac.ut.ece.ie.auth;

import ir.ac.ut.ece.ie.testdata.TestDataFactory;
import ir.ac.ut.ece.ie.users.User;
import ir.ac.ut.ece.ie.users.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthServiceTest {
    @MockitoBean
    private UserRepository userRepository;
    @MockitoBean
    private AuthRepository authRepository;
    @Autowired
    private AuthService authService;

    @Test
    void login_notFoundUsername_throwsException() {
        var request = new LoginRequest();
        request.setUsername("username");

        assertThrows(UsernameOrPasswordIncorrectException.class, () -> authService.login(request));
    }

    @Test
    void login_incorrectPassword_throwsException() {
        var request = new LoginRequest();
        request.setUsername("username");
        request.setPassword("incorrect");

        var user = new User();
        user.setPassword("correct");

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));

        assertThrows(UsernameOrPasswordIncorrectException.class, () -> authService.login(request));
    }

    @Test
    void login_alreadyLoggedIn_returnsUserDto() {
        var request = new LoginRequest();
        request.setUsername("username");
        request.setPassword("correct");

        var user = TestDataFactory.sampleCustomerUser();
        user.setPassword("correct");

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));

        var userDto = authService.login(request);
        assertEquals(user.getUsername(), userDto.getUsername());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(user.getRole().toString(), userDto.getRole());
    }

    @Test
    void login_validInput_returnsUserDto() {
        var request = new LoginRequest();
        request.setUsername("username");
        request.setPassword("correct");

        var user = TestDataFactory.sampleCustomerUser();
        user.setPassword("correct");

        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(user));

        var userDto = authService.login(request);
        assertEquals(user.getUsername(), userDto.getUsername());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(user.getRole().toString(), userDto.getRole());
    }

    @Test
    void getCurrentUser_notLoggedIn_returnsNull() {
        SecurityContextHolder.clearContext();

        var user = authService.getCurrentUser();

        assertNull(user);
    }

    @Test
    void getCurrentUser_loggedIn_returnsUserDto() {
        var user = TestDataFactory.sampleCustomerUser();
        var userId = user.getId();
        var authentication = new UsernamePasswordAuthenticationToken(
                userId,null);

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        var userDto = authService.getCurrentUser();
        assertEquals(user.getUsername(), userDto.getUsername());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(user.getRole().toString(), userDto.getRole());
    }
}
