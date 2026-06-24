package ir.ac.ut.ece.ie.purchases;

import ir.ac.ut.ece.ie.common.NotCustomerException;
import ir.ac.ut.ece.ie.common.UserNotFoundException;
import ir.ac.ut.ece.ie.testdata.TestDataFactory;
import ir.ac.ut.ece.ie.users.CustomerRepository;
import ir.ac.ut.ece.ie.users.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PurchaseServiceTest {
    @MockitoBean
    private PurchaseRepository purchaseRepository;
    @MockitoBean
    private PurchaseItemRepository purchaseItemRepository;
    @MockitoBean
    private UserRepository userRepository;
    @MockitoBean
    private CustomerRepository customerRepository;
    @Autowired
    private PurchaseService purchaseService;

    @Test
    void getAllPurchases_userNotFound_throwsException() {
        assertThrows(UserNotFoundException.class, () -> purchaseService.getAllPurchases("username"));
    }

    @Test
    void getAllPurchases_notCustomerUser_throwsException() {
        var user = TestDataFactory.sampleAdminUser();

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));

        assertThrows(NotCustomerException.class, () -> purchaseService.getAllPurchases("username"));
    }

    @Test
    void getAllPurchases_emptyPurchaseHistory_returnsEmptyPurchaseHistoryDto() {
        var user = TestDataFactory.sampleCustomerUser();

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(purchaseRepository.findByUsername(any())).thenReturn(List.of());
        when(customerRepository.findByUsername(any())).thenReturn(Optional.of(user));

        var purchaseHistoryDto = purchaseService.getAllPurchases("username");

        assertEquals(0, purchaseHistoryDto.getPurchaseHistory().size());
    }

    @Test
    void getAllPurchases_validInput_returnsPurchaseHistoryDto() {
        var user = TestDataFactory.sampleCustomerUser();

        var purchase = TestDataFactory.samplePurchaseWithOneBorrowItem(user);
        var book = purchase.getItems().stream().findFirst().orElseThrow().getBook();

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(purchaseRepository.findByUsername(user.getUsername())).thenReturn(List.of(purchase));
        when(customerRepository.findByUsername(any())).thenReturn(Optional.of(user));

        var purchaseHistoryDto = purchaseService.getAllPurchases(user.getUsername());

        assertEquals(user.getUsername(), purchaseHistoryDto.getUsername());
        assertEquals(1, purchaseHistoryDto.getPurchaseHistory().size());

        var purchaseDto = purchaseHistoryDto.getPurchaseHistory().get(0);
//        assertEquals(purchase.getDate(), purchaseDto.getPurchaseDate());
        assertEquals(purchase.getTotalCost(), purchaseDto.getTotalCost());
        assertEquals(1, purchaseDto.getItems().size());

        var purchaseItemDto = purchaseDto.getItems().get(0);
        assertEquals(book.getTitle(), purchaseItemDto.getTitle());
        assertEquals(book.getAuthor().getName(), purchaseItemDto.getAuthor());
        assertEquals(book.getPublisher(), purchaseItemDto.getPublisher());
        assertEquals(book.getGenres(), purchaseItemDto.getGenres());
        assertEquals(book.getYear(), purchaseItemDto.getYear());
        assertEquals(true, purchaseItemDto.getIsBorrowed());
        assertEquals(book.getPrice(), purchaseItemDto.getPrice());
        assertEquals(purchase.getTotalCost(), purchaseItemDto.getFinalPrice());
    }

    @Test
    void getPurchasedBooks_userNotFound_throwsException() {
        assertThrows(UserNotFoundException.class, () -> purchaseService.getPurchasedBooks("username"));
    }

    @Test
    void getPurchasedBooks_notCustomerUser_throwsException() {
        var user = TestDataFactory.sampleAdminUser();
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));

        assertThrows(NotCustomerException.class, () -> purchaseService.getPurchasedBooks("username"));
    }

    @Test
    void getPurchasedBooks_validInput_returnsPurchasedBooksHistory() {
        var user = TestDataFactory.sampleCustomerUser();

        var purchase = TestDataFactory.samplePurchaseWithOneBorrowItem(user);
        var purchaseItem = purchase.getItems().stream().findFirst().orElseThrow();
        var book = purchaseItem.getBook();

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(purchaseItemRepository.findNotExpiredPurchaseItems(any(), any())).thenReturn(List.of(purchaseItem));
        when(customerRepository.findByUsername(any())).thenReturn(Optional.of(user));

        var purchasedBooksHistory= purchaseService.getPurchasedBooks(user.getUsername());

        assertEquals(user.getUsername(), purchasedBooksHistory.getUsername());
        assertEquals(1, purchasedBooksHistory.getBooks().size());

        var purchasedBookDto = purchasedBooksHistory.getBooks().get(0);

        assertEquals(book.getTitle(), purchasedBookDto.getTitle());
        assertEquals(book.getAuthor().getName(), purchasedBookDto.getAuthor());
        assertEquals(book.getPublisher(), purchasedBookDto.getPublisher());
        assertEquals(book.getGenreNames(), purchasedBookDto.getGenres());
        assertEquals(book.getYear(), purchasedBookDto.getYear());
        assertEquals(purchaseItem.getPrice(), purchasedBookDto.getPrice());
        assertEquals(true, purchasedBookDto.getIsBorrowed());
    }
}
