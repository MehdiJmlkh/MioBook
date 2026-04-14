package ir.ac.ut.ece.ie.credits;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/credits")
public class CreditController {
    private final CreditService creditService;

    @PostMapping
    public ResponseEntity<Void> addCredit(@Valid @RequestBody AddCreditRequest request) {
        creditService.addCredit(request);
        return ResponseEntity.ok().build();
    }
}
