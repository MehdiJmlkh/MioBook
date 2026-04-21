package ir.ac.ut.ece.ie.credits;

import ir.ac.ut.ece.ie.auth.AuthRepository;
import ir.ac.ut.ece.ie.auth.NotLoggedInException;
import ir.ac.ut.ece.ie.common.NotCustomerException;
import ir.ac.ut.ece.ie.common.UserNotFoundException;
import ir.ac.ut.ece.ie.testdata.TestDataFactory;
import ir.ac.ut.ece.ie.users.Role;
import ir.ac.ut.ece.ie.users.User;
import ir.ac.ut.ece.ie.users.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CreditServiceTest {
    @MockitoBean
    private UserRepository userRepository;
    @MockitoBean
    private AuthRepository authRepository;
    @Autowired
    private CreditService creditService;

    @Test
    void addCredit_userNotFound_throwsException() {
        var request = new AddCreditRequest();
        assertThrows(UserNotFoundException.class, () -> creditService.addCredit(request));
    }

    @Test
    void addCredit_notCustomerUser_throwsException() {
        var request = new AddCreditRequest();
        var user = TestDataFactory.sampleAdminUser();

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        assertThrows(NotCustomerException.class, () -> creditService.addCredit(request));
    }

    @Test
    void addCredit_validInput_addsCredit() {
        var request = new AddCreditRequest();
        request.setUsername("username");
        request.setCredit(100);

        var user = new User();
        user.setRole(Role.CUSTOMER);
        user.setBalance(10);

        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(user));
        when(authRepository.isLoggedIn(user)).thenReturn(true);

        creditService.addCredit(request);

        assertEquals(110, user.getBalance());
    }
}
