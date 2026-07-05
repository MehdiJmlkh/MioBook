package ir.ac.ut.ece.ie.credits;

import ir.ac.ut.ece.ie.common.NotCustomerException;
import ir.ac.ut.ece.ie.common.UserNotFoundException;
import ir.ac.ut.ece.ie.testdata.TestDataFactory;
import ir.ac.ut.ece.ie.users.*;
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
    private CustomerRepository customerRepository;
    @Autowired
    private CreditService creditService;

    @Test
    void addCredit_userNotFound_throwsException() {
        var request = new AddCreditRequest();
        assertThrows(UserNotFoundException.class, () -> creditService.addCredit(request));
    }

    @Test
    void addCredit_validInput_addsCredit() {
        var request = new AddCreditRequest();
        request.setUsername("username");
        request.setCredit(100);

        var user = new Customer();
        user.setBalance(10);

        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(user));
        when(customerRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(user));

        creditService.addCredit(request);

        assertEquals(110, user.getBalance());
    }
}
