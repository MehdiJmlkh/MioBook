package ir.ac.ut.ece.ie.carts;

import ir.ac.ut.ece.ie.auth.AuthRepository;
import ir.ac.ut.ece.ie.auth.NotLoggedInException;
import ir.ac.ut.ece.ie.common.BookNotFoundException;
import ir.ac.ut.ece.ie.common.NotCustomerException;
import ir.ac.ut.ece.ie.common.UserNotFoundException;
import ir.ac.ut.ece.ie.books.BookRepository;
import ir.ac.ut.ece.ie.purchases.Purchase;
import ir.ac.ut.ece.ie.purchases.PurchaseSummaryDto;
import ir.ac.ut.ece.ie.purchases.PurchaseRepository;
import ir.ac.ut.ece.ie.users.CustomerRepository;
import ir.ac.ut.ece.ie.users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CartService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;
    private final PurchaseRepository purchaseRepository;
    private final CartMapper cartMapper;
    private final AuthRepository authRepository;

    public CartDto getCart(String username) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        customerRepository.findByUsername(username)
                .orElseThrow(NotCustomerException::new);

        var cart = cartRepository.findByUser(user)
                .orElse(new Cart());

        var items = cart.getItems().stream()
                .map(CartItemDto::fromCartItem)
                .toList();

        var cartDto = new CartDto();
        cartDto.setUsername(username);
        cartDto.setTotalCost(cart.getTotalPrice());
        cartDto.setItems(items);

        return cartDto;
    }

    public CartItemDto addItemToCart(AddCartRequest request) {
        var book = bookRepository.findByTitle(request.getTitle())
                .orElseThrow(BookNotFoundException::new);

        userRepository.findByUsername(request.getUsername())
                .orElseThrow(UserNotFoundException::new);

        var user = customerRepository.findByUsername(request.getUsername())
                .orElseThrow(NotCustomerException::new);

        var cart = cartRepository.findByUser(user).orElseThrow();

        if (cart.getItems().size() >= 10) {
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

        userRepository.findByUsername(request.getUsername())
                .orElseThrow(UserNotFoundException::new);

        var user = customerRepository.findByUsername(request.getUsername())
                .orElseThrow(NotCustomerException::new);

        cartRepository.findByUser(user).ifPresent(
                cart -> {
                    if (cart.getItems().size() >= 10) {
                        throw new CartIsFullException();
                    }
                }
        );

        var cartItem = CartItem.BorrowCartItem(book, request.getDays());

        var cart = cartRepository.findByUser(user).orElseThrow();
        cart.addItem(cartItem);
        cartRepository.save(cart);
    }

    public void removeItemFromCart(Long bookId) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(BookNotFoundException::new);

        var user = authRepository.getAuthenticatedUser()
                .orElseThrow(NotLoggedInException::new);

        customerRepository.findByUsername(user.getUsername())
                .orElseThrow(NotCustomerException::new);

        var cart = cartRepository.findByUser(user)
                .orElseThrow(BookNotInCartException::new);

        if (!cart.contains(book)) {
            throw new BookNotInCartException();
        }

        cart.removeBook(book);
    }

    public PurchaseSummaryDto purchaseCart(PurchaseCartRequest request) {
        userRepository.findByUsername(request.getUsername())
                .orElseThrow(UserNotFoundException::new);

        var user = customerRepository.findByUsername(request.getUsername())
                .orElseThrow(NotCustomerException::new);

        var cart = cartRepository.findByUser(user)
                .orElseThrow(EmptyCartException::new);

        if (cart.isEmpty()) {
            throw new EmptyCartException();
        }

        if (!authRepository.isLoggedIn(user)) {
            throw new NotLoggedInException();
        }

        if (cart.getTotalPrice() > user.getBalance()) {
            throw new NotEnoughCreditException(cart.getTotalPrice() - user.getBalance());
        }

        var purchase = new Purchase();
        purchase.setUser(user);
        var purchaseItems = cart.getItems().stream()
                        .map(cartMapper::toPurchaseItem)
                                .collect(Collectors.toSet());
        purchaseItems.forEach(purchaseItem -> purchaseItem.setDate(LocalDateTime.now()));
        purchase.setItems(purchaseItems);
        purchase.setDate(LocalDateTime.now());
        purchase.setTotalCost(cart.getTotalPrice());

        user.withdrawCredit(cart.getTotalPrice());
        cartRepository.delete(cart);
        purchaseRepository.save(purchase);

        return PurchaseSummaryDto.builder()
                .bookCount(purchase.getItems().size())
                .totalCost(purchase.getTotalCost())
                .date(purchase.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }
}
