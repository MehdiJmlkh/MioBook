package ir.ac.ut.ece.ie.carts;

import ir.ac.ut.ece.ie.books.Book;
import ir.ac.ut.ece.ie.books.BookRepository;
import ir.ac.ut.ece.ie.common.BookNotFoundException;
import ir.ac.ut.ece.ie.common.NotCustomerException;
import ir.ac.ut.ece.ie.common.UserNotFoundException;
import ir.ac.ut.ece.ie.users.Role;
import ir.ac.ut.ece.ie.users.User;
import ir.ac.ut.ece.ie.users.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CartServiceTest {
    @MockitoBean
    private CartRepository cartRepository;
    @MockitoBean
    private UserRepository userRepository;
    @MockitoBean
    private BookRepository bookRepository;

    @Autowired
    private CartService cartService;

    @Test
    void addItemToCart_bookNotFound_throwsException() {
        var request = new AddCartRequest();
        assertThrows(BookNotFoundException.class, () -> cartService.addItemToCart(request));
    }

    @Test
    void addItemToCart_userNotFound_throwsException() {
        var request = new AddCartRequest();
        when(bookRepository.findByTitle(any())).thenReturn(Optional.of(new Book()));
        assertThrows(UserNotFoundException.class, () -> cartService.addItemToCart(request));
    }

    @Test
    void addItemToCart_notCustomerUser_throwsException() {
        var request = new AddCartRequest();

        var user = new User();
        user.setRole(Role.ADMIN);

        when(bookRepository.findByTitle(any())).thenReturn(Optional.of(new Book()));
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));

        assertThrows(NotCustomerException.class, () -> cartService.addItemToCart(request));
    }

    @Test
    void addItemToCart_cartIsFull_throwsException() {
        var request = new AddCartRequest();

        var user = new User();
        user.setRole(Role.CUSTOMER);

        var cart = new Cart();
        IntStream.range(0, 10)
                .forEach(i -> cart.getItems().add(new CartItem()));

        when(bookRepository.findByTitle(any())).thenReturn(Optional.of(new Book()));
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(cartRepository.findByUser(any())).thenReturn(Optional.of(cart));

        assertThrows(CartIsFullException.class, () -> cartService.addItemToCart(request));
    }

    @Test
    void addItemToCart_validInput_addsCartItem() {
        var request = AddCartRequest.builder()
                .username("username")
                .title("title")
                .build();

        var book = new Book();
        book.setPrice(10);

        var user = new User();
        user.setRole(Role.CUSTOMER);

        when(bookRepository.findByTitle(request.getTitle())).thenReturn(Optional.of(book));
        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(user));
        when(cartRepository.findByUser(user)).thenReturn(Optional.of(new Cart()));


        cartService.addItemToCart(request);

        verify(cartRepository).addItemToCart(eq(user), argThat( item ->
                item.getIsBorrowed().equals(false) &&
                item.getBook().equals(book) &&
                item.getPrice().equals(book.getPrice())
        ));
    }

    @Test
    void removeItemFromCart_bookNotFound_throwsException() {
        var request = new RemoveCartRequest();
        assertThrows(BookNotFoundException.class, () -> cartService.removeItemFromCart(request));
    }

    @Test
    void removeItemFromCart_userNotFound_throwsException() {
        var request = new RemoveCartRequest();
        when(bookRepository.findByTitle(any())).thenReturn(Optional.of(new Book()));
        assertThrows(UserNotFoundException.class, () -> cartService.removeItemFromCart(request));
    }

    @Test
    void removeItemFromCart_notCustomerUser_throwsException() {
        var request = new RemoveCartRequest();

        var user = new User();
        user.setRole(Role.ADMIN);

        when(bookRepository.findByTitle(any())).thenReturn(Optional.of(new Book()));
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));

        assertThrows(NotCustomerException.class, () -> cartService.removeItemFromCart(request));
    }

    @Test
    void removeItemFromCart_bookNotInCart_throwsException() {
        var request = new RemoveCartRequest();

        var user = new User();
        user.setRole(Role.CUSTOMER);

        when(bookRepository.findByTitle(any())).thenReturn(Optional.of(new Book()));
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(cartRepository.findByUser(any())).thenReturn(Optional.of(new Cart()));

        assertThrows(BookNotInCartException.class, () -> cartService.removeItemFromCart(request));
    }

    @Test
    void removeItemFromCart_cartNotExists_throwsException() {
        var request = new RemoveCartRequest();

        var user = new User();
        user.setRole(Role.CUSTOMER);

        when(bookRepository.findByTitle(any())).thenReturn(Optional.of(new Book()));
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(cartRepository.findByUser(any())).thenReturn(Optional.empty());

        assertThrows(BookNotInCartException.class, () -> cartService.removeItemFromCart(request));
    }

    @Test
    void removeItemFromCart_validInput_removesCartItem() {
        var request = new RemoveCartRequest();

        var user = new User();
        user.setRole(Role.CUSTOMER);

        var book = new Book();

        var cartItem = new CartItem();
        cartItem.setBook(book);

        var cart = new Cart();
        cart.getItems().add(cartItem);

        when(bookRepository.findByTitle(any())).thenReturn(Optional.of(book));
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(cartRepository.findByUser(any())).thenReturn(Optional.of(cart));

        cartService.removeItemFromCart(request);

        assertFalse(cart.contains(book));
    }
}
