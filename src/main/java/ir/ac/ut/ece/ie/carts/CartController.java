package ir.ac.ut.ece.ie.carts;

import ir.ac.ut.ece.ie.common.ErrorDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/carts")
public class CartController {
    private final CartService cartService;

    @PostMapping("/add")
    public Cart addCart(@RequestBody AddCartRequest request) {
        return cartService.addCart(request);
    }

    @DeleteMapping
    public ResponseEntity<Void> removeCart(@RequestBody RemoveCartRequest request) {
        cartService.removeCart(request);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(BookNotInCartException.class)
    public void handleBookNotInCartException() {
        ResponseEntity.badRequest()
                .body(new ErrorDto("Cart does not contain the book."));
    }
}
