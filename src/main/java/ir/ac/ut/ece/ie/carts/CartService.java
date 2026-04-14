package ir.ac.ut.ece.ie.carts;

import ir.ac.ut.ece.ie.common.BookNotFoundException;
import ir.ac.ut.ece.ie.common.NotCustomerException;
import ir.ac.ut.ece.ie.common.UserNotFoundException;
import ir.ac.ut.ece.ie.books.BookRepository;
import ir.ac.ut.ece.ie.users.Role;
import ir.ac.ut.ece.ie.users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CartService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

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
                if (cart.getBooks().size() >= 10) {
                    throw new CartIsFullException();
                }
            }
        );

        cartRepository.addCart(user, book);

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

        cart.getBooks().remove(book);
    }

}
