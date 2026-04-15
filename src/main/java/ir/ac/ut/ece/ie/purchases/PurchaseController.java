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
    public PurchaseListDto getPurchase(@PathVariable("username") String username) {
        return purchaseService.getPurchase(username);
    }
}
