package ir.ac.ut.ece.ie.books;

import ir.ac.ut.ece.ie.authors.Author;
import ir.ac.ut.ece.ie.authors.AuthorRepository;
import ir.ac.ut.ece.ie.common.AuthorNotFoundException;
import ir.ac.ut.ece.ie.common.BookNotFoundException;
import ir.ac.ut.ece.ie.common.NotAdminException;
import ir.ac.ut.ece.ie.common.UserNotFoundException;
import ir.ac.ut.ece.ie.purchases.Purchase;
import ir.ac.ut.ece.ie.purchases.PurchaseItem;
import ir.ac.ut.ece.ie.purchases.PurchaseRepository;
import ir.ac.ut.ece.ie.reviews.Review;
import ir.ac.ut.ece.ie.testdata.TestDataFactory;
import ir.ac.ut.ece.ie.users.Role;
import ir.ac.ut.ece.ie.users.User;
import ir.ac.ut.ece.ie.users.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookServiceTest {

    @MockitoBean
    private UserRepository userRepository;
    @MockitoBean
    private AuthorRepository authorRepository;
    @MockitoBean
    private BookRepository bookRepository;
    @MockitoBean
    private PurchaseRepository purchaseRepository;
    @Autowired
    private BookService bookService;

    @Test
    void addBook_duplicateTitle_throwsException() {
        var request = new AddBookRequest();
        request.setTitle("title");
        when(bookRepository.findByTitle(request.getTitle())).thenReturn(Optional.of(new Book()));
        assertThrows(BookTitleAlreadyExistsException.class, () -> bookService.addBook(request));
    }

    @Test
    void addBook_authorNotFound_throwsException() {
        var request = new AddBookRequest();
        assertThrows(AuthorNotFoundException.class, () -> bookService.addBook(request));
    }

    @Test
    void addBook_userNotFound_throwsException() {
        var request = new AddBookRequest();
        when(authorRepository.findByName(any())).thenReturn(Optional.of(new Author()));
        assertThrows(UserNotFoundException.class, () -> bookService.addBook(request));
    }

    @Test
    void addBook_notAdminUser_throwsException() {
        var request = new AddBookRequest();
        when(authorRepository.findByName(any())).thenReturn(Optional.of(new Author()));

        var user = TestDataFactory.sampleCustomerUser();

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));

        assertThrows(NotAdminException.class, () -> bookService.addBook(request));
    }

    @Test
    void addBook_validInput_addBook() {
        var request = AddBookRequest.builder()
                .username("username")
                .title("title")
                .author("author")
                .publisher("publisher")
                .year(2000)
                .price(10)
                .synopsis("synopsis")
                .content("content")
                .genres(Set.of("genre"))
                .build();

        var author = new Author();
        when(authorRepository.findByName(request.getAuthor())).thenReturn(Optional.of(author));

        var user = TestDataFactory.sampleAdminUser();

        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(user));

        bookService.addBook(request);

        verify(bookRepository).addBook(argThat(book ->
                book.getTitle().equals(request.getTitle()) &&
                book.getAuthor().equals(author) &&
                book.getPublisher().equals(request.getPublisher()) &&
                book.getYear().equals(request.getYear()) &&
                book.getGenres().equals(request.getGenres()) &&
                book.getPrice().equals(request.getPrice()) &&
                book.getSynopsis().equals(request.getSynopsis()) &&
                book.getContent().equals(request.getContent())
        ));
    }

    @Test
    void getBook_bookNotFound_throwsException() {
        assertThrows(BookNotFoundException.class, () -> bookService.getBook("title"));
    }

    @Test
    void getBook_validInput_returnsBookDto() {
        var author = new Author();
        author.setName("author's name");

        var review1 = new Review();
        review1.setRate(3);

        var review2 = new Review();
        review2.setRate(4);

        var book = Book.builder()
                .title("title")
                .author(author)
                .publisher("publisher")
                .year(2000)
                .genres(Set.of("genres"))
                .price(100)
                .synopsis("synopsis")
                .content("content").reviews(Set.of(review1, review2)).build();

        when(bookRepository.findByTitle(book.getTitle())).thenReturn(Optional.of(book));

        var bookDto = bookService.getBook("title");
        assertEquals(book.getTitle(), bookDto.getTitle());
        assertEquals(author.getName(), bookDto.getAuthor());
        assertEquals(book.getPublisher(), bookDto.getPublisher());
        assertEquals(book.getYear(), bookDto.getYear());
        assertEquals(book.getGenres(), bookDto.getGenres());
        assertEquals(book.getPrice(), bookDto.getPrice());
        assertEquals(book.getSynopsis(), bookDto.getSynopsis());
        assertEquals(3.5, bookDto.getAverageRating());
    }

    @Test
    void getBookContent_bookNotFound_throwsException() {
        assertThrows(BookNotFoundException.class, () -> bookService.getBookContent("username", "title"));
    }

    @Test
    void getBookContent_userNotFound_throwsException() {
        when(bookRepository.findByTitle(any())).thenReturn(Optional.of(new Book()));
        assertThrows(UserNotFoundException.class, () -> bookService.getBookContent("username", "title"));
    }

    @Test
    void getBookContent_notPurchasedBook_throwsException() {
        when(bookRepository.findByTitle(any())).thenReturn(Optional.of(new Book()));
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(new User()));
        when(purchaseRepository.findByUsernameAndTitle(any(), any())).thenReturn(Optional.empty());

        assertThrows(BookNotInStockException.class, () -> bookService.getBookContent("username", "title"));
    }

    @Test
    void getBookContent_expiredBorrowedBook_throwsException() {
        var purchase = mock(PurchaseItem.class);

        when(purchase.hasExpired()).thenReturn(true);
        when(purchase.getIsBorrowed()).thenReturn(true);

        when(bookRepository.findByTitle(any())).thenReturn(Optional.of(new Book()));
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(new User()));
        when(purchaseRepository.findByUsernameAndTitle(any(), any())).thenReturn(Optional.of(purchase));

        assertThrows(BookNotInStockException.class, () -> bookService.getBookContent("username", "title"));
    }

    @Test
    void getBookContent_validInput_returnsContent() {
        String username = "username";
        String title = "title";

        var purchase = new PurchaseItem();
        var book = new Book();
        purchase.setBook(book);
        purchase.setIsBorrowed(false);

        book.setContent("content");
        book.setTitle(title);

        when(bookRepository.findByTitle(title)).thenReturn(Optional.of(book));
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(new User()));
        when(purchaseRepository.findByUsernameAndTitle(username, title)).thenReturn(Optional.of(purchase));

        var bookContentDto = bookService.getBookContent(username, title);

        assertEquals(title, bookContentDto.getTitle());
        assertEquals(book.getContent(), bookContentDto.getContent());
    }
}
