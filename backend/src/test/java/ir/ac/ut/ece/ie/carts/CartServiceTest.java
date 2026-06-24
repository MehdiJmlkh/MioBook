package ir.ac.ut.ece.ie.carts;

import ir.ac.ut.ece.ie.auth.AuthRepository;
import ir.ac.ut.ece.ie.auth.AuthService;
import ir.ac.ut.ece.ie.books.Book;
import ir.ac.ut.ece.ie.books.BookRepository;
import ir.ac.ut.ece.ie.common.BookNotFoundException;
import ir.ac.ut.ece.ie.common.NotCustomerException;
import ir.ac.ut.ece.ie.common.UserNotFoundException;
import ir.ac.ut.ece.ie.purchases.Purchase;
import ir.ac.ut.ece.ie.purchases.PurchaseItem;
import ir.ac.ut.ece.ie.purchases.PurchaseRepository;
import ir.ac.ut.ece.ie.testdata.TestDataFactory;
import ir.ac.ut.ece.ie.users.*;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
    private CartItemRepository cartItemRepository;
    @MockitoBean
    private UserRepository userRepository;
    @MockitoBean
    private CustomerRepository customerRepository;
    @MockitoBean
    private BookRepository bookRepository;
    @MockitoBean
    private PurchaseRepository purchaseRepository;
    @MockitoBean
    private AuthRepository authRepository;
    @MockitoBean
    private AuthService authService;

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

        var user = TestDataFactory.sampleAdminUser();

        when(bookRepository.findByTitle(any())).thenReturn(Optional.of(new Book()));
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));

        assertThrows(NotCustomerException.class, () -> cartService.addItemToCart(request));
    }

    @Test
    void addItemToCart_cartIsFull_throwsException() {
        var request = new AddCartRequest();

        var user = TestDataFactory.sampleCustomerUser();

        var cart = new Cart();
        IntStream.range(0, 10)
                .forEach(i -> cart.getItems().add(new CartItem()));

        when(bookRepository.findByTitle(any())).thenReturn(Optional.of(new Book()));
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(customerRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(cartRepository.findByUser(any())).thenReturn(Optional.of(cart));
        when(cartRepository.countCartItems(user)).thenReturn(10L);

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
        book.setAuthor(TestDataFactory.sampleAuthor());

        var user = TestDataFactory.sampleCustomerUser();

        when(bookRepository.findByTitle(request.getTitle())).thenReturn(Optional.of(book));
        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(user));
        when(customerRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(user));

        when(cartRepository.findByUser(user)).thenReturn(Optional.of(new Cart()));


        cartService.addItemToCart(request);

        var captor = ArgumentCaptor.forClass(Cart.class);
        verify(cartRepository).save(captor.capture());
        var newCart = captor.getValue();

        assertEquals(1, newCart.getItems().size());

        var cartItem = newCart.getItems().get(0);
        assertEquals(false, cartItem.getIsBorrowed());
        assertEquals(book, cartItem.getBook());
        assertEquals(book.getPrice(), cartItem.getPrice());
    }


    @Test
    void removeItemFromCart_notCustomerUser_throwsException() {
        var user = TestDataFactory.sampleAdminUser();

        when(authService.me()).thenReturn(user);

        assertThrows(NotCustomerException.class, () -> cartService.removeItemFromCart(1L));
    }

    @Test
    void removeItemFromCart_cartItemNotFound_throwsException() {
        var user = TestDataFactory.sampleCustomerUser();

        when(authService.me()).thenReturn(user);
        when(customerRepository.findByUsername(any())).thenReturn(Optional.of(user));

        assertThrows(CartItemNotFoundException.class, () -> cartService.removeItemFromCart(1L));
    }

    @Test
    void removeItemFromCart_validInput_removesCartItem() {
        var user = TestDataFactory.sampleCustomerUser();

        var cartItem = new CartItem();
        cartItem.setId(1L);

        when(authService.me()).thenReturn(user);
        when(customerRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(cartItemRepository.findByIdAndUser(cartItem.getId() ,user)).thenReturn(Optional.of(cartItem));

        cartService.removeItemFromCart(cartItem.getId());

        verify(cartItemRepository).delete(cartItem);
    }

    @Test
    void purchaseCart_userNotFound_throwsException() {
        var request = new PurchaseCartRequest();

        assertThrows(UserNotFoundException.class, () -> cartService.purchaseCart(request));
    }

    @Test
    void purchaseCart_notCustomerUser_throwsException() {
        var request = new PurchaseCartRequest();

        var user = TestDataFactory.sampleAdminUser();

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));

        assertThrows(NotCustomerException.class, () -> cartService.purchaseCart(request));
    }

    @Test
    void purchaseCart_cartNotExists_throwsException() {
        var request = new PurchaseCartRequest();

        var user = TestDataFactory.sampleCustomerUser();

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(customerRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(cartRepository.findByUser(any())).thenReturn(Optional.empty());

        assertThrows(EmptyCartException.class, () -> cartService.purchaseCart(request));
    }

    @Test
    void purchaseCart_emptyCart_throwsException() {
        var request = new PurchaseCartRequest();

        var user = TestDataFactory.sampleCustomerUser();

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(customerRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(cartRepository.findByUser(any())).thenReturn(Optional.of(new Cart()));

        assertThrows(EmptyCartException.class, () -> cartService.purchaseCart(request));
    }

    @Test
    void purchaseCart_notEnoughCredit_throwsException() {
        var request = new PurchaseCartRequest();

        var user = new Customer();
        user.setBalance(50);

        var cart = new Cart();
        var cartItem = new CartItem();
        cartItem.setPrice(100);
        cart.addItem(cartItem);

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(customerRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(cartRepository.findByUser(any())).thenReturn(Optional.of(cart));

        assertThrows(NotEnoughCreditException.class, () -> cartService.purchaseCart(request));
    }

    @Test
    void purchaseCart_validInput_addsToPurchaseRepository() {
        var request = new PurchaseCartRequest();
        request.setUsername("username");

        var user = new Customer();
        user.setBalance(110);

        var book = new Book();
        book.setPrice(100);
        var cartItem = CartItem.BuyCartItem(book);

        var cart = new Cart();
        cart.addItem(cartItem);

        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(user));
        when(customerRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(user));
        when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));

        LocalDateTime before = LocalDateTime.now();
        var purchaseSummary = cartService.purchaseCart(request);
        LocalDateTime after = LocalDateTime.now();

        var captor = ArgumentCaptor.forClass(Purchase.class);
        verify(purchaseRepository).save(captor.capture());

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

        var cartCaptor = ArgumentCaptor.forClass(Cart.class);
        verify(cartRepository).save(cartCaptor.capture());
        var savedCart = cartCaptor.getValue();

        assertEquals(0, savedCart.getItems().size());

        assertEquals(1, purchaseSummary.getBookCount());
        assertEquals(100, purchaseSummary.getTotalCost());
        String expected = purchase.getDate()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        assertEquals(expected, purchaseSummary.getDate());
    }

    @Test
    void addBorrowedItemToCart_itemNotFound_throwsException() {
        var request = new BorrowBookRequest();
        assertThrows(BookNotFoundException.class, () -> cartService.addBorrowedItemToCart(request));
    }

    @Test
    void addBorrowedItemToCart_userNotFound_throwsException() {
        var request = new BorrowBookRequest();

        when(bookRepository.findByTitle(any())).thenReturn(Optional.of(new Book()));

        assertThrows(UserNotFoundException.class, () -> cartService.addBorrowedItemToCart(request));
    }

    @Test
    void addBorrowedItemToCart_notCustomerUser_throwsException() {
        var request = new BorrowBookRequest();

        var user = TestDataFactory.sampleAdminUser();

        when(bookRepository.findByTitle(any())).thenReturn(Optional.of(new Book()));
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));

        assertThrows(NotCustomerException.class, () -> cartService.addBorrowedItemToCart(request));
    }

    @Test
    void addBorrowedItemToCart_validInput_addsCartItem() {
        var request = new BorrowBookRequest();
        request.setUsername("username");
        request.setTitle("title");
        request.setDays(5);

        var book = new Book();
        book.setPrice(100);
        when(bookRepository.findByTitle(request.getTitle())).thenReturn(Optional.of(book));

        var user = TestDataFactory.sampleCustomerUser();
        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(user));
        when(customerRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(user));
        when(cartRepository.findByUser(user)).thenReturn(Optional.of(new Cart()));

        cartService.addBorrowedItemToCart(request);

        var captor = ArgumentCaptor.forClass(Cart.class);
        verify(cartRepository).save(captor.capture());
        var cart = captor.getValue();

        assertEquals(1, cart.getItems().size());

        var cartItem = cart.getItems().get(0);
        assertTrue(cartItem.getIsBorrowed());
        assertEquals(5, cartItem.getBorrowDays());
        assertEquals(book, cartItem.getBook());
        assertEquals(50, cartItem.getPrice());
    }

    @Test
    void getCart_userNotFound_throwsException() {
        assertThrows(UserNotFoundException.class, () -> cartService.getCart("username"));
    }

    @Test
    void getCart_notCustomerUser_throwsException() {
        var user = TestDataFactory.sampleAdminUser();

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));

        assertThrows(NotCustomerException.class, () -> cartService.getCart("username"));
    }

    @Test
    void getCart_emptyCart_returnsEmptyCartDto() {
        var user = TestDataFactory.sampleCustomerUser();

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(customerRepository.findByUsername(any())).thenReturn(Optional.of(user));

        var cartDto = cartService.getCart("username");

        assertEquals(0, cartDto.getItems().size());
        assertEquals(0, cartDto.getTotalCost());
    }

    @Test
    void getCart_buyCartItem_returnsCartDto() {
        var user = TestDataFactory.sampleCustomerUser();

        var book = TestDataFactory.sampleBook();
        var cartItem = CartItem.BuyCartItem(book);

        var cart = new Cart();
        cart.setUser(user);
        cart.setItems(List.of(cartItem));

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(customerRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(cartRepository.findByUser(eq(user))).thenReturn(Optional.of(cart));

        var cartDto = cartService.getCart("username");

        assertEquals(user.getUsername(), cartDto.getUsername());
        assertEquals(1, cartDto.getItems().size());
        assertEquals(15, cartDto.getTotalCost());

        var cartItemDto = cartDto.getItems().get(0);
        assertEquals(book.getTitle(), cartItemDto.getTitle());
        assertEquals(book.getAuthor().getName(), cartItemDto.getAuthor());
        assertEquals(book.getPublisher(), cartItemDto.getPublisher());
        assertEquals(book.getGenreNames(), cartItemDto.getGenres());
        assertEquals(book.getYear(), cartItemDto.getYear());
        assertEquals(book.getPrice(), cartItemDto.getPrice());
        assertEquals(false, cartItemDto.getIsBorrowed());
        assertEquals(book.getPrice(), cartItemDto.getFinalPrice());
    }

    @Test
    void getCart_borrowCartItem_returnsCartDto() {
        var user = TestDataFactory.sampleCustomerUser();

        var book = TestDataFactory.sampleBook();

        var cartItem = CartItem.BorrowCartItem(book, 5);

        var cart = new Cart();
        cart.setUser(user);
        cart.setItems(List.of(cartItem));

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(customerRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(cartRepository.findByUser(eq(user))).thenReturn(Optional.of(cart));

        var cartDto = cartService.getCart("username");

        assertEquals(user.getUsername(), cartDto.getUsername());
        assertEquals(1, cartDto.getItems().size());
        assertEquals(7, cartDto.getTotalCost());

        var cartItemDto = cartDto.getItems().get(0);
        assertEquals(book.getTitle(), cartItemDto.getTitle());
        assertEquals(book.getAuthor().getName(), cartItemDto.getAuthor());
        assertEquals(book.getPublisher(), cartItemDto.getPublisher());
        assertEquals(book.getGenreNames(), cartItemDto.getGenres());
        assertEquals(book.getYear(), cartItemDto.getYear());
        assertEquals(book.getPrice(), cartItemDto.getPrice());
        assertEquals(true, cartItemDto.getIsBorrowed());
        assertEquals(5, cartItem.getBorrowDays());
        assertEquals(7, cartItemDto.getFinalPrice());
    }
}
