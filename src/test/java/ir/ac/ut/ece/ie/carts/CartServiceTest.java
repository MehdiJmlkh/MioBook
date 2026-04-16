package ir.ac.ut.ece.ie.carts;

import ir.ac.ut.ece.ie.books.Book;
import ir.ac.ut.ece.ie.books.BookRepository;
import ir.ac.ut.ece.ie.common.BookNotFoundException;
import ir.ac.ut.ece.ie.common.NotCustomerException;
import ir.ac.ut.ece.ie.common.UserNotFoundException;
import ir.ac.ut.ece.ie.purchases.Purchase;
import ir.ac.ut.ece.ie.purchases.PurchaseItem;
import ir.ac.ut.ece.ie.purchases.PurchaseRepository;
import ir.ac.ut.ece.ie.users.Role;
import ir.ac.ut.ece.ie.users.User;
import ir.ac.ut.ece.ie.users.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    @MockitoBean
    private PurchaseRepository purchaseRepository;

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

    @Test
    void purchaseCart_userNotFound_throwsException() {
        var request = new PurchaseCartRequest();

        assertThrows(UserNotFoundException.class, () -> cartService.purchaseCart(request));
    }

    @Test
    void purchaseCart_notCustomerUser_throwsException() {
        var request = new PurchaseCartRequest();

        var user = new User();
        user.setRole(Role.ADMIN);
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));

        assertThrows(NotCustomerException.class, () -> cartService.purchaseCart(request));
    }

    @Test
    void purchaseCart_cartNotExists_throwsException() {
        var request = new PurchaseCartRequest();

        var user = new User();
        user.setRole(Role.CUSTOMER);

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(cartRepository.findByUser(any())).thenReturn(Optional.empty());

        assertThrows(EmptyCartException.class, () -> cartService.purchaseCart(request));
    }

    @Test
    void purchaseCart_emptyCart_throwsException() {
        var request = new PurchaseCartRequest();

        var user = new User();
        user.setRole(Role.CUSTOMER);

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(cartRepository.findByUser(any())).thenReturn(Optional.of(new Cart()));

        assertThrows(EmptyCartException.class, () -> cartService.purchaseCart(request));
    }

    @Test
    void purchaseCart_notEnoughCredit_throwsException() {
        var request = new PurchaseCartRequest();

        var user = new User();
        user.setRole(Role.CUSTOMER);
        user.setBalance(50);

        var cart = new Cart();
        var cartItem = new CartItem();
        cartItem.setPrice(100);
        cart.addItem(cartItem);

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(cartRepository.findByUser(any())).thenReturn(Optional.of(cart));

        assertThrows(NotEnoughCreditException.class, () -> cartService.purchaseCart(request));
    }

    @Test
    void purchaseCart_validInput_addsToPurchaseRepository() {
        var request = new PurchaseCartRequest();
        request.setUsername("username");

        var user = new User();
        user.setRole(Role.CUSTOMER);
        user.setBalance(110);

        var book = new Book();
        book.setPrice(100);
        var cartItem = CartItem.BuyCartItem(book);

        var cart = new Cart();
        cart.addItem(cartItem);

        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(user));
        when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));

        LocalDateTime before = LocalDateTime.now();
        var purchaseSummary = cartService.purchaseCart(request);
        LocalDateTime after = LocalDateTime.now();

        var captor = ArgumentCaptor.forClass(Purchase.class);
        verify(purchaseRepository).addPurchase(captor.capture());

        Purchase purchase = captor.getValue();
        PurchaseItem purchaseItem = purchase.getItems().iterator().next();

        assertEquals(100, purchase.getTotalCost());
        assertTrue(purchase.getDate().isAfter(before));
        assertTrue(purchase.getDate().isBefore(after));
        assertEquals(user, purchase.getUser());

        assertFalse(purchaseItem.getIsBorrowed());
        assertEquals(book, purchaseItem.getBook());
        assertEquals(100, purchaseItem.getPrice());
        assertTrue(purchaseItem.getDate().isAfter(before));
        assertTrue(purchaseItem.getDate().isBefore(after));

        assertEquals(10, user.getBalance());

        verify(cartRepository).removeCart(eq(cart));

        assertEquals(1, purchaseSummary.getBookCount());
        assertEquals(100, purchaseSummary.getTotalCost());
        String expected = purchase.getDate()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        assertEquals(expected, purchaseSummary.getDate());
    }

    @Test
    void addBorrowedBookToCart_bookNotFound_throwsException() {
        var request = new BorrowBookRequest();
        assertThrows(BookNotFoundException.class, () -> cartService.addBorrowedBookToCart(request));
    }

    @Test
    void addBorrowedBookToCart_userNotFound_throwsException() {
        var request = new BorrowBookRequest();

        when(bookRepository.findByTitle(any())).thenReturn(Optional.of(new Book()));

        assertThrows(UserNotFoundException.class, () -> cartService.addBorrowedBookToCart(request));
    }

    @Test
    void addBorrowedBookToCart_notCustomerUser_throwsException() {
        var request = new BorrowBookRequest();

        var user = new User();
        user.setRole(Role.ADMIN);

        when(bookRepository.findByTitle(any())).thenReturn(Optional.of(new Book()));
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));

        assertThrows(NotCustomerException.class, () -> cartService.addBorrowedBookToCart(request));
    }

    @Test
    void addBorrowedBookToCart_validInput_addsCartItem() {
        var request = new BorrowBookRequest();
        request.setUsername("username");
        request.setTitle("title");
        request.setDays(5);

        var book = new Book();
        book.setPrice(100);
        when(bookRepository.findByTitle(request.getTitle())).thenReturn(Optional.of(book));

        var user = new User();
        user.setRole(Role.CUSTOMER);
        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(user));

        cartService.addBorrowedBookToCart(request);

        var captor = ArgumentCaptor.forClass(CartItem.class);
        verify(cartRepository).addItemToCart(eq(user), captor.capture());
        var cartItem = captor.getValue();

        assertTrue(cartItem.getIsBorrowed());
        assertEquals(5, cartItem.getBorrowDays());
        assertEquals(book, cartItem.getBook());
        assertEquals(50, cartItem.getPrice());
    }
}
