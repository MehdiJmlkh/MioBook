package ir.ac.ut.ece.ie.credits;

import ir.ac.ut.ece.ie.auth.AuthService;
import ir.ac.ut.ece.ie.common.UserNotFoundException;
import ir.ac.ut.ece.ie.users.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CreditService {
    private final AuthService authService;
    private final CustomerRepository customerRepository;

    public Integer addCredit(AddCreditRequest request) {
        var user = authService.currentCustomer();

        user.addCredit(request.getCredit());
        customerRepository.save(user);
        return user.getBalance();
    }

    public Integer getBalance(String username) {
        var user = customerRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        return  user.getBalance();
    }
}
