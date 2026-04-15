package ir.ac.ut.ece.ie.carts;

import ir.ac.ut.ece.ie.common.BookNotFoundException;
import ir.ac.ut.ece.ie.common.NotCustomerException;
import ir.ac.ut.ece.ie.common.UserNotFoundException;
import ir.ac.ut.ece.ie.books.BookRepository;
import ir.ac.ut.ece.ie.purchases.Purchase;
import ir.ac.ut.ece.ie.purchases.PurchaseSummaryDto;
import ir.ac.ut.ece.ie.purchases.PurchaseRepository;
import ir.ac.ut.ece.ie.users.Role;
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
    private final CartRepository cartRepository;
    private final PurchaseRepository purchaseRepository;
    private final CartMapper cartMapper;

    public CartDto getCart(String username) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        if (user.getRole() != Role.CUSTOMER) {
            throw new NotCustomerException();
        }

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

    public Cart addItemToCart(AddCartRequest request) {
        var book = bookRepository.findByTitle(request.getTitle())
                .orElseThrow(BookNotFoundException::new);

        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(UserNotFoundException::new);

        if (user.getRole() != Role.CUSTOMER) {
            throw new NotCustomerException();
        }

        cartRepository.findByUser(user).ifPresent(
            cart -> {
                if (cart.getItems().size() >= 10) {
                    throw new CartIsFullException();
                }
            }
        );

        var cartItem = CartItem.BuyCartItem(book);
        cartRepository.addItemToCart(user, cartItem);

        return cartRepository.findByUser(user).orElseThrow();
    }

    public void removeItemFromCart(RemoveCartRequest request) {
        var book = bookRepository.findByTitle(request.getTitle())
                .orElseThrow(BookNotFoundException::new);

        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(UserNotFoundException::new);

        if (user.getRole() != Role.CUSTOMER) {
            throw new NotCustomerException();
        }

        var cart = cartRepository.findByUser(user)
                .orElseThrow(BookNotFoundException::new);

        if (!cart.contains(book)) {
            throw new BookNotInCartException();
        }

        cart.removeBook(book);
    }

    public void addBorrowedBookToCart(BorrowBookRequest request) {
        var book = bookRepository.findByTitle(request.getTitle())
                .orElseThrow(BookNotFoundException::new);

        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(UserNotFoundException::new);

        if (user.getRole() != Role.CUSTOMER) {
            throw new NotCustomerException();
        }

        var cartItem = CartItem.BorrowCartItem(book, request.getDays());

        cartRepository.addItemToCart(user, cartItem);
    }

    public PurchaseSummaryDto purchaseCart(PurchaseCartRequest request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(UserNotFoundException::new);

        var cart = cartRepository.findByUser(user)
                .orElseThrow(EmptyCartException::new);

        if (cart.isEmpty()) {
            throw new EmptyCartException();
        }

        if (cart.getTotalPrice() > user.getBalance()) {
            throw new NotEnoughCreditException();
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
        cartRepository.removeCart(cart);
        purchaseRepository.addPurchase(purchase);

        return PurchaseSummaryDto.builder()
                .bookCount(purchase.getItems().size())
                .totalCost(purchase.getTotalCost())
                .date(purchase.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }
}
