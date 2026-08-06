package ir.ac.ut.ece.ie.carts;

import ir.ac.ut.ece.ie.common.ErrorDto;
import ir.ac.ut.ece.ie.purchases.PurchaseSummaryDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/carts")
public class CartController {
    private final CartService cartService;

    @GetMapping("/{username}")
    public CartDto getCart(@PathVariable("username") String username) {
        return cartService.getCart(username);
    }

    @PostMapping("/items")
    public CartItemDto addItemToCart(@RequestBody AddCartRequest request) {
        return cartService.addItemToCart(request);
    }

    @PostMapping("/borrowed-items")
    public ResponseEntity<Void> addBorrowedItemToCart(@RequestBody BorrowBookRequest request) {
        cartService.addBorrowedItemToCart(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<Void> removeItemFromCart(@PathVariable(name = "cartItemId") Long cartItemId) {
        cartService.removeItemFromCart(cartItemId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/purchase")
    public ResponseEntity<PurchaseSummaryDto> purchaseCart(@RequestBody PurchaseCartRequest request) {
        var purchase = cartService.purchaseCart(request);

        return ResponseEntity.ok(purchase);
    }

    @ExceptionHandler(CartIsFullException.class)
    public ResponseEntity<ErrorDto> handleCartIsFullException() {
        return ResponseEntity.badRequest()
                .body(new ErrorDto("Cart is full"));
    }

    @ExceptionHandler(CartItemNotFoundException.class)
    public ResponseEntity<ErrorDto> handleCartItemNotFoundException() {
        return ResponseEntity.badRequest()
                .body(new ErrorDto("Cart item not found."));
    }

    @ExceptionHandler(EmptyCartException.class)
    public ResponseEntity<ErrorDto> handleEmptyCartException() {
        return ResponseEntity.badRequest()
                .body(new ErrorDto("Cart is empty."));
    }

    @ExceptionHandler(NotEnoughCreditException.class)
    public ResponseEntity<ErrorDto> handleNotEnoughCreditException(NotEnoughCreditException exception) {
        return ResponseEntity.badRequest()
                .body(new ErrorDto("$" + (exception.getExtraCredit() / 100.0) + " more credit needed."));
    }
}
