package ir.ac.ut.ece.ie.carts;

import org.springframework.stereotype.Repository;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class PurchaseRepository {
    private final Set<Purchase> purchases = new LinkedHashSet<>();

    public void addPurchase(Purchase purchase) {
        purchases.add(purchase);
    }

    public Optional<PurchaseItem> findByUsernameAndTitle(String username, String title) {
        return purchases.stream()
                .filter(purchase -> purchase.getUser().getUsername().equals(username))
                .flatMap(purchase -> purchase.getItems().stream())
                .filter(purchaseItem -> purchaseItem.getBook().getTitle().equals(title))
                .findFirst();
    }
}
