package ir.ac.ut.ece.ie.carts;

import ir.ac.ut.ece.ie.auth.AuthService;
import ir.ac.ut.ece.ie.common.BookNotFoundException;
import ir.ac.ut.ece.ie.books.BookRepository;
import ir.ac.ut.ece.ie.purchases.Purchase;
import ir.ac.ut.ece.ie.purchases.PurchaseSummaryDto;
import ir.ac.ut.ece.ie.purchases.PurchaseRepository;
import ir.ac.ut.ece.ie.users.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CartService {
    private final BookRepository bookRepository;
    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final PurchaseRepository purchaseRepository;
    private final CartMapper cartMapper;
    private final AuthService authService;

    public CartDto getCart(String username) {
        var user = authService.me();

        var cart = cartRepository.findByUser(user)
                .orElse(new Cart());

        var items = cart.getItems().stream()
                .map(CartItemDto::fromCartItem)
                .toList();

        var cartDto = new CartDto();
        cartDto.setUsername(user.getUsername());
        cartDto.setTotalCost(cart.getTotalPrice());
        cartDto.setItems(items);

        return cartDto;
    }

    public CartItemDto addItemToCart(AddCartRequest request) {
        var book = bookRepository.findByTitle(request.getTitle())
                .orElseThrow(BookNotFoundException::new);

        var user = authService.currentCustomer();

        var cart = cartRepository.findByUser(user).orElseThrow();

        if (cartRepository.countCartItems(user) >= 10) {
            throw new CartIsFullException();
        }

        var cartItem = CartItem.BuyCartItem(book);
        cart.addItem(cartItem);
        cartRepository.save(cart);

        return CartItemDto.fromCartItem(cartItem);
    }

    public void addBorrowedItemToCart(BorrowBookRequest request) {
        var book = bookRepository.findByTitle(request.getTitle())
                .orElseThrow(BookNotFoundException::new);

        var user = authService.me();

        if (cartRepository.countCartItems(user) >= 10) {
            throw new CartIsFullException();
        }

        var cartItem = CartItem.BorrowCartItem(book, request.getDays());

        var cart = cartRepository.findByUser(user).orElseThrow();
        cart.addItem(cartItem);
        cartRepository.save(cart);
    }

    public void removeItemFromCart(Long cartItemId) {
        var user = authService.me();

        var cartItem = cartItemRepository.findByIdAndUser(cartItemId, user)
                .orElseThrow(CartItemNotFoundException::new);

        cartItemRepository.delete(cartItem);
    }

    public PurchaseSummaryDto purchaseCart(PurchaseCartRequest request) {
        var user = authService.currentCustomer();

        var cart = cartRepository.findByUser(user)
                .orElseThrow(EmptyCartException::new);

        if (cart.isEmpty()) {
            throw new EmptyCartException();
        }

        if (cart.getTotalPrice() > user.getBalance()) {
            throw new NotEnoughCreditException(cart.getTotalPrice() - user.getBalance());
        }

        var purchase = new Purchase();
        var purchaseItems = cart.getItems().stream()
                        .map(cartMapper::toPurchaseItem)
                                .collect(Collectors.toSet());
        purchaseItems.forEach(purchaseItem -> {
            purchaseItem.setDate(LocalDateTime.now());
            purchase.addItem(purchaseItem);
        });
        purchase.setDate(LocalDateTime.now());
        purchase.setTotalCost(cart.getTotalPrice());

        user.withdrawCredit(cart.getTotalPrice());
        user.addPurchase(purchase);

        cart.clear();
        cartRepository.save(cart);
        purchaseRepository.save(purchase);

        return PurchaseSummaryDto.builder()
                .bookCount(purchase.getItems().size())
                .totalCost(purchase.getTotalCost())
                .date(purchase.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }
}
