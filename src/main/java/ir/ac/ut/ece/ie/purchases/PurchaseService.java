package ir.ac.ut.ece.ie.purchases;

import ir.ac.ut.ece.ie.common.NotCustomerException;
import ir.ac.ut.ece.ie.common.UserNotFoundException;
import ir.ac.ut.ece.ie.users.Role;
import ir.ac.ut.ece.ie.users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final PurchaseMapper purchaseMapper;

    public PurchaseHistoryDto getAllPurchases(String username) {
        var user = userRepository.findByUsername(username)
                        .orElseThrow(UserNotFoundException::new);

        if (user.getRole() != Role.CUSTOMER) {
            throw new NotCustomerException();
        }

        var purchases = purchaseRepository.findByUsername(username);

        var purchaseDtos = purchases.stream()
                .map(purchaseMapper::toDto)
                .toList();

        return PurchaseHistoryDto.builder()
                .username(username)
                .purchaseHistory(purchaseDtos)
                .build();
    }
}
