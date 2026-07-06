package ir.ac.ut.ece.ie.credits;

import ir.ac.ut.ece.ie.auth.AuthService;
import ir.ac.ut.ece.ie.users.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CreditServiceTest {
    @MockitoBean
    private AuthService authService;
    @MockitoBean
    private CustomerRepository customerRepository;
    @Autowired
    private CreditService creditService;

    @Test
    void addCredit_validInput_addsCredit() {
        var request = new AddCreditRequest();
        request.setUsername("username");
        request.setCredit(100);

        var user = new Customer();
        user.setBalance(10);

        when(authService.currentCustomer()).thenReturn(user);
        when(customerRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(user));

        creditService.addCredit(request);

        assertEquals(110, user.getBalance());
    }
}
