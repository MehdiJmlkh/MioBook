package ir.ac.ut.ece.ie.credits;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/credits")
public class CreditController {
    private final CreditService creditService;

    @GetMapping("/{username}")
    public ResponseEntity<Integer> getBalance(@PathVariable("username") String username) {
        var balance = creditService.getBalance(username);
        return ResponseEntity.ok(balance);
    }

    @PostMapping
    public ResponseEntity<Integer> addCredit(@Valid @RequestBody AddCreditRequest request) {
        var newBalance = creditService.addCredit(request);
        return ResponseEntity.ok(newBalance);
    }
}
