package ir.ac.ut.ece.ie.purchases;

import ir.ac.ut.ece.ie.auth.AuthRepository;
import ir.ac.ut.ece.ie.common.NotCustomerException;
import ir.ac.ut.ece.ie.common.UserNotFoundException;
import ir.ac.ut.ece.ie.users.CustomerRepository;
import ir.ac.ut.ece.ie.users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final PurchaseItemRepository purchaseItemRepository;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final AuthRepository authRepository;
    private final PurchaseMapper purchaseMapper;

    public PurchaseHistoryDto getAllPurchases(String username) {
        var user = userRepository.findByUsername(username)
                        .orElseThrow(UserNotFoundException::new);

        customerRepository.findByUsername(username)
                .orElseThrow(NotCustomerException::new);

        var purchases = purchaseRepository.findByUsername(username);

        var purchaseDtoList = purchases.stream()
                .map(purchaseMapper::toDto)
                .toList();

        return PurchaseHistoryDto.builder()
                .username(username)
                .purchaseHistory(purchaseDtoList)
                .build();
    }

    public PurchasedBooksHistory getPurchasedBooks(String username) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        customerRepository.findByUsername(username)
                .orElseThrow(NotCustomerException::new);

        var items = purchaseItemRepository.findNotExpiredPurchaseItems(username, LocalDateTime.now());

        var purchasedBooks = items.stream()
                .map(purchaseMapper::toDto)
                .toList();

        var purchasedBooksHistory = new PurchasedBooksHistory();
        purchasedBooksHistory.setUsername(username);
        purchasedBooksHistory.setBooks(purchasedBooks);

        return purchasedBooksHistory;
    }

    public PurchasedBookStatus getPurchasedBookStatus(String username, Long id) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        customerRepository.findByUsername(username)
                .orElseThrow(NotCustomerException::new);

        var purchases = purchaseRepository.findByUsername(username);

        var purchaseBook = purchases.stream()
                .flatMap(purchase -> purchase.getItems().stream())
                .filter(purchaseItem -> !purchaseItem.hasExpired())
                .filter(purchaseItem -> purchaseItem.getBook().getId().equals(id))
                .findFirst()
                .orElse(null);

        if (purchaseBook == null) {
            return PurchasedBookStatus.Available;
        }
        if (purchaseBook.getIsBorrowed()) {
            return PurchasedBookStatus.Borrowed;
        }
        return PurchasedBookStatus.Owned;
   }
}
