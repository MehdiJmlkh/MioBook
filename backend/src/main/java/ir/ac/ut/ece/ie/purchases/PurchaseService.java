package ir.ac.ut.ece.ie.purchases;

import ir.ac.ut.ece.ie.auth.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;

@AllArgsConstructor
@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final PurchaseItemRepository purchaseItemRepository;
    private final PurchaseMapper purchaseMapper;
    private final AuthService authService;

    public PurchaseHistoryDto getAllPurchases(String _username) {
        var username = authService.currentUsername();
        var purchases = purchaseRepository.findByUsername(username);

        var purchaseDtoList = purchases.stream()
                .sorted(Comparator.comparing(Purchase::getDate).reversed())
                .map(purchaseMapper::toDto)
                .toList();

        return PurchaseHistoryDto.builder()
                .username(username)
                .purchaseHistory(purchaseDtoList)
                .build();
    }

    public PurchasedBooksHistory getPurchasedBooks(String _username) {
        var username = authService.currentUsername();
        var items = purchaseItemRepository.findNotExpiredPurchaseItems(username, LocalDateTime.now());

        var purchasedBooks = items.stream()
                .map(purchaseMapper::toDto)
                .toList();

        var purchasedBooksHistory = new PurchasedBooksHistory();
        purchasedBooksHistory.setUsername(username);
        purchasedBooksHistory.setBooks(purchasedBooks);

        return purchasedBooksHistory;
    }

    public PurchasedBookStatus getPurchasedBookStatus(String _username, Long id) {
        var username = authService.currentUsername();
        var purchaseItem = purchaseItemRepository
                .findNotExpiredPurchaseItems(username, id, LocalDateTime.now())
                .stream()
                .findFirst()
                .orElse(null);

        if (purchaseItem == null) {
            return PurchasedBookStatus.Available;
        }
        if (purchaseItem.getIsBorrowed()) {
            return PurchasedBookStatus.Borrowed;
        }
        return PurchasedBookStatus.Owned;
   }
}
