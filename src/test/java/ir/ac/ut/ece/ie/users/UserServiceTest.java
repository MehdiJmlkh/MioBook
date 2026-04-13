package ir.ac.ut.ece.ie.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserServiceTest {
    private final UserService userService = new UserService();

    private AddUserRequest addUserRequest;

    @BeforeEach
    void setup() {
        addUserRequest = AddUserRequest.builder()
                .role("customer")
                .email("user@domain.com")
                .username("user")
                .password("1234")
                .build();
    }

    @Test
    void addUser_ValidArgs_ReturnsSuccess() {
        Response response = userService.addUser(addUserRequest);
        assertTrue(response.getSuccess());
    }

    @Test
    void addUser_ShortPassword_ReturnsFailed() {
        addUserRequest.setPassword("123");
        Response response = userService.addUser(addUserRequest);
        assertFalse(response.getSuccess());
    }

    @Test
    void addUser_InvalidEmail_ReturnsFailed() {
        addUserRequest.setEmail("invalid.com");
        Response response = userService.addUser(addUserRequest);
        assertFalse(response.getSuccess());
    }

    @Test
    void addUser_InvalidRole_ReturnsFailed() {
        addUserRequest.setRole("invalid");
        Response response = userService.addUser(addUserRequest);
        assertFalse(response.getSuccess());
    }
}
