package ir.ac.ut.ece.ie.carts;

import ir.ac.ut.ece.ie.common.BookNotFoundException;
import ir.ac.ut.ece.ie.common.NotCustomerException;
import ir.ac.ut.ece.ie.common.UserNotFoundException;
import ir.ac.ut.ece.ie.books.BookRepository;
import ir.ac.ut.ece.ie.users.Role;
import ir.ac.ut.ece.ie.users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Service
public class CartService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final PurchaseRepository purchaseRepository;

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

    public void borrowBook(BorrowBookRequest request) {
        var book = bookRepository.findByTitle(request.getTitle())
                .orElseThrow(BookNotFoundException::new);

        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(UserNotFoundException::new);

        if (user.getRole() != Role.CUSTOMER) {
            throw new NotCustomerException();
        }

        var cart = cartRepository.findByUser(user);

        var cartItem = CartItem.BorrowCartItem(book, request.getDays());

        cartRepository.addItemToCart(user, cartItem);
    }

    public PurchaseDto purchaseCart(PurchaseCartRequest request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(UserNotFoundException::new);

        var cart = cartRepository.findByUser(user)
                .orElseThrow(EmptyCartException::new);

        if (cart.isEmpty()) {
            throw new EmptyCartException();
        }

        if (cart.getTotalPrice() > user.getCredit()) {
            throw new NotEnoughCreditException();
        }

        var purchase = new Purchase();
        purchase.setUser(user);
        purchase.setBooks(cart.getBooks());
        purchase.setDate(LocalDateTime.now());
        purchase.setTotalCost(cart.getTotalPrice());

        user.withdrawCredit(cart.getTotalPrice());
        return PurchaseDto.builder()
                .bookCount(purchase.getBooks().size())
                .totalCost(purchase.getTotalCost())
                .date(purchase.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }
}
