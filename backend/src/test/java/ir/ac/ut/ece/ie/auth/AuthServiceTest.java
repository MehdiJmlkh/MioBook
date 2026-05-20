package ir.ac.ut.ece.ie.auth;

import ir.ac.ut.ece.ie.testdata.TestDataFactory;
import ir.ac.ut.ece.ie.users.User;
import ir.ac.ut.ece.ie.users.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
    void login_anotherUserLoggedIn_throwsException() {
        var request = new LoginRequest();
        request.setUsername("username");
        request.setPassword("correct");

        var user = new User();
        user.setPassword("correct");

        var anotherUser = new User();
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(authRepository.getAuthenticatedUser()).thenReturn(Optional.of(anotherUser));

        assertThrows(AnotherUserAlreadyLoggedInException.class, () -> authService.login(request));
    }

    @Test
    void login_alreadyLoggedIn_returnsUserDto() {
        var request = new LoginRequest();
        request.setUsername("username");
        request.setPassword("correct");

        var user = TestDataFactory.sampleCustomerUser();
        user.setPassword("correct");

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(authRepository.getAuthenticatedUser()).thenReturn(Optional.of(user));

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
    void logout_notLoggedIn_throwsException() {
        assertThrows(NotLoggedInException.class, () -> authService.logout());
    }

    @Test
    void logout_validInput_removesAuthenticatedUser() {
        when(authRepository.getAuthenticatedUser()).thenReturn(Optional.of(new User()));
        authService.logout();
        verify(authRepository).removeAuthenticatedUser();
    }

    @Test
    void getLoggedInUser_notLoggedIn_returnsNull() {
        var user = authService.getLoggedInUser();
        assertNull(user);
    }

    @Test
    void getLoggedInUser_loggedIn_returnsUserDto() {
        var user = TestDataFactory.sampleCustomerUser();
        when(authRepository.getAuthenticatedUser()).thenReturn(Optional.of(user));

        var userDto = authService.getLoggedInUser();
        assertEquals(user.getUsername(), userDto.getUsername());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(user.getRole().toString(), userDto.getRole());
    }
}
