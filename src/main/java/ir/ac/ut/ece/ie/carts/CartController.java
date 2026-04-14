package ir.ac.ut.ece.ie.carts;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/carts")
public class CartController {

    @PostMapping("/add")
    public ResponseEntity<Void> addCart() {
        return ResponseEntity.ok().build();
    }
}
