package ir.ac.ut.ece.ie.purchases;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/purchases")
public class PurchaseController {
    private final PurchaseService purchaseService;

    @GetMapping("/{username}")
    public PurchaseHistoryDto getAllPurchases(@PathVariable("username") String username) {
        return purchaseService.getAllPurchases(username);
    }

    @GetMapping("/{username}/books")
    public PurchasedBooksHistory getPurchasedBooks(@PathVariable("username") String username) {
        return purchaseService.getPurchasedBooks(username);
    }

    @GetMapping("/{username}/books/{id}/status")
    public PurchasedBookStatus getPurchasedBookStatus(@PathVariable("username") String username,
                                                  @PathVariable("id") Long id) {
        return purchaseService.getPurchasedBookStatus(username, id);
    }
}
