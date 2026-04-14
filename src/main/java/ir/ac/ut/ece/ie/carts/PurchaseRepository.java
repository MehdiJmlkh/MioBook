package ir.ac.ut.ece.ie.carts;

import org.springframework.stereotype.Repository;

import java.util.LinkedHashSet;
import java.util.Set;

@Repository
public class PurchaseRepository {
    private final Set<Purchase> purchases = new LinkedHashSet<>();

    public void addPurchase(Purchase purchase) {
        purchases.add(purchase);
    }
}
