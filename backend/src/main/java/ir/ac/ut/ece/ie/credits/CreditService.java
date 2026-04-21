package ir.ac.ut.ece.ie.credits;

import ir.ac.ut.ece.ie.auth.AuthRepository;
import ir.ac.ut.ece.ie.auth.NotLoggedInException;
import ir.ac.ut.ece.ie.common.NotCustomerException;
import ir.ac.ut.ece.ie.common.UserNotFoundException;
import ir.ac.ut.ece.ie.users.Role;
import ir.ac.ut.ece.ie.users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CreditService {
    private final UserRepository userRepository;
    private final AuthRepository authRepository;

    public void addCredit(AddCreditRequest request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(UserNotFoundException::new);

        if (user.getRole() != Role.CUSTOMER) {
            throw new NotCustomerException();
        }

        if (!authRepository.isLoggedIn(user)) {
            throw new NotLoggedInException();
        }

        user.addCredit(request.getCredit());
    }
}
