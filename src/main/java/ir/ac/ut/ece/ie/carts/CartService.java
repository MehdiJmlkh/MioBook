package ir.ac.ut.ece.ie.carts;

import ir.ac.ut.ece.ie.common.UserNotFoundException;
import ir.ac.ut.ece.ie.books.BookRepository;
import ir.ac.ut.ece.ie.users.Response;
import ir.ac.ut.ece.ie.users.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CartService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public Response addCart(AddCartRequest request) {
        var book = bookRepository.findByTitle(request.getTitle())
                .orElseThrow(BookNotFoundException::new);

        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(UserNotFoundException::new);

        if (user.getRole().equals("admin")) {
            throw new NotCustomerException();
        }


        return null;
    }

}
