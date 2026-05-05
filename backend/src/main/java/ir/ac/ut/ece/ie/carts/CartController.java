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

    @PostMapping
    public Cart addItemToCart(@RequestBody AddCartRequest request) {
        return cartService.addItemToCart(request);
    }

    @DeleteMapping
    public ResponseEntity<Void> removeItemFromCart(@RequestBody RemoveCartRequest request) {
        cartService.removeItemFromCart(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/purchase")
    public ResponseEntity<PurchaseSummaryDto> purchaseCart(@RequestBody PurchaseCartRequest request) {
        var purchase = cartService.purchaseCart(request);

        return ResponseEntity.ok(purchase);
    }

    @PostMapping("/borrow")
    public ResponseEntity<Void> borrowBook(@RequestBody BorrowBookRequest request) {
        cartService.addBorrowedBookToCart(request);
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

    @ExceptionHandler(EmptyCartException.class)
    public ResponseEntity<ErrorDto> handleEmptyCartException() {
        return ResponseEntity.badRequest()
                .body(new ErrorDto("Cart is empty."));
    }

    @ExceptionHandler(NotEnoughCreditException.class)
    public ResponseEntity<ErrorDto> handleNotEnoughCreditException() {
        return ResponseEntity.badRequest()
                .body(new ErrorDto("Credit is not enough."));
    }
}
