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
    public Cart addItemToCart(@RequestBody AddCartRequest request) {
        return cartService.addItemToCart(request);
    }

    @DeleteMapping
    public ResponseEntity<Void> removeItemFromCart(@RequestBody RemoveCartRequest request) {
        cartService.removeItemFromCart(request);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(CartIsFullException.class)
    public ResponseEntity<ErrorDto> handleCartIsFullException() {
        return ResponseEntity.badRequest()
                .body(new ErrorDto("Cart is full."));
    }

    @ExceptionHandler(BookNotInCartException.class)
    public ResponseEntity<ErrorDto> handleBookNotInCartException() {
        return ResponseEntity.badRequest()
                .body(new ErrorDto("Cart does not contain the book."));
    }
}
