package ir.ac.ut.ece.ie.purchases;

import ir.ac.ut.ece.ie.authors.Author;
import ir.ac.ut.ece.ie.books.Book;
import ir.ac.ut.ece.ie.common.NotCustomerException;
import ir.ac.ut.ece.ie.common.UserNotFoundException;
import ir.ac.ut.ece.ie.users.Role;
import ir.ac.ut.ece.ie.users.User;
import ir.ac.ut.ece.ie.users.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PurchaseServiceTest {
    @MockitoBean
    private PurchaseRepository purchaseRepository;
    @MockitoBean
    private UserRepository userRepository;
    @Autowired
    private PurchaseService purchaseService;

    @Test
    void getAllPurchases_userNotFound_throwsException() {
        assertThrows(UserNotFoundException.class, () -> purchaseService.getAllPurchases("username"));
    }

    @Test
    void getAllPurchases_notCustomerUser_throwsException() {
        var user = new User();
        user.setRole(Role.ADMIN);

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));

        assertThrows(NotCustomerException.class, () -> purchaseService.getAllPurchases("username"));
    }

    @Test
    void getAllPurchases_emptyPurchaseHistory_returnsEmptyPurchaseHistoryDto() {
        var user = new User();
        user.setRole(Role.CUSTOMER);

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(purchaseRepository.findByUsername(any())).thenReturn(List.of());

        var purchaseHistoryDto = purchaseService.getAllPurchases("username");

        assertEquals(0, purchaseHistoryDto.getPurchaseHistory().size());
    }

    @Test
    void getAllPurchases_validInput_returnsPurchaseHistoryDto() {
        var user = new User();
        user.setRole(Role.CUSTOMER);
        user.setUsername("username");

        var author = new Author();
        author.setName("name");

        var book = new Book();
        book.setTitle("title");
        book.setAuthor(author);
        book.setPrice(15);

        var purchaseItem = PurchaseItem.builder()
                .book(book)
                .isBorrowed(true)
                .borrowDays(5)
                .price(7)
                .date(LocalDateTime.now())
                .build();

        var purchase = new Purchase();
        purchase.setTotalCost(100);
        purchase.setUser(user);
        purchase.setDate(LocalDateTime.now());
        purchase.getItems().add(purchaseItem);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(purchaseRepository.findByUsername(user.getUsername())).thenReturn(List.of(purchase));

        var purchaseHistoryDto = purchaseService.getAllPurchases("username");

        assertEquals(user.getUsername(), purchaseHistoryDto.getUsername());
        assertEquals(1, purchaseHistoryDto.getPurchaseHistory().size());

        var purchaseDto = purchaseHistoryDto.getPurchaseHistory().get(0);
//        assertEquals(purchase.getDate(), purchaseDto.getPurchaseDate());
        assertEquals(purchase.getTotalCost(), purchaseDto.getTotalCost());
        assertEquals(1, purchaseDto.getItems().size());

        var purchaseItemDto = purchaseDto.getItems().get(0);
        assertEquals(book.getTitle(), purchaseItemDto.getTitle());
        assertEquals(author.getName(), purchaseItemDto.getAuthor());
        assertEquals(book.getPublisher(), purchaseItemDto.getPublisher());
        assertEquals(book.getGenres(), purchaseItemDto.getGenres());
        assertEquals(book.getYear(), purchaseItemDto.getYear());
        assertEquals(purchaseItem.getIsBorrowed(), purchaseItemDto.getIsBorrowed());
        assertEquals(15, purchaseItemDto.getPrice());
        assertEquals(7, purchaseItemDto.getFinalPrice());
    }

    @Test
    void getPurchasedBooks_userNotFound_throwsException() {
        assertThrows(UserNotFoundException.class, () -> purchaseService.getPurchasedBooks("username"));
    }

    @Test
    void getPurchasedBooks_notCustomerUser_throwsException() {
        var user = new User();
        user.setRole(Role.ADMIN);
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));

        assertThrows(NotCustomerException.class, () -> purchaseService.getPurchasedBooks("username"));
    }

    @Test
    void getPurchasedBooks_validInput_returnsPurchasedBooksHistory() {
        var user = new User();
        user.setUsername("username");
        user.setRole(Role.CUSTOMER);

        var author = new Author();
        author.setName("name");

        var book = new Book();
        book.setTitle("title");
        book.setAuthor(author);
        book.setPrice(15);

        var purchaseItem = PurchaseItem.builder()
                .book(book)
                .isBorrowed(true)
                .borrowDays(5)
                .price(7)
                .date(LocalDateTime.now())
                .build();

        var purchase = new Purchase();
        purchase.setTotalCost(100);
        purchase.setUser(user);
        purchase.setDate(LocalDateTime.now());
        purchase.getItems().add(purchaseItem);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(purchaseRepository.findByUsername(user.getUsername())).thenReturn(List.of(purchase));

        var purchasedBooksHistory= purchaseService.getPurchasedBooks("username");

        assertEquals(user.getUsername(), purchasedBooksHistory.getUsername());
        assertEquals(1, purchasedBooksHistory.getBooks().size());

        var purchasedBookDto = purchasedBooksHistory.getBooks().get(0);

        assertEquals(book.getTitle(), purchasedBookDto.getTitle());
        assertEquals(author.getName(), purchasedBookDto.getAuthor());
        assertEquals(book.getPublisher(), purchasedBookDto.getPublisher());
        assertEquals(book.getGenres(), purchasedBookDto.getGenres());
        assertEquals(book.getYear(), purchasedBookDto.getYear());
        assertEquals(purchaseItem.getPrice(), purchasedBookDto.getPrice());
        assertEquals(purchaseItem.getIsBorrowed(), purchasedBookDto.getIsBorrowed());
    }
}
