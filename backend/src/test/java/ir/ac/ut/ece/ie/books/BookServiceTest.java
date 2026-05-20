package ir.ac.ut.ece.ie.books;

import ir.ac.ut.ece.ie.auth.AuthRepository;
import ir.ac.ut.ece.ie.auth.NotLoggedInException;
import ir.ac.ut.ece.ie.authors.Author;
import ir.ac.ut.ece.ie.authors.AuthorRepository;
import ir.ac.ut.ece.ie.common.AuthorNotFoundException;
import ir.ac.ut.ece.ie.common.BookNotFoundException;
import ir.ac.ut.ece.ie.common.NotAdminException;
import ir.ac.ut.ece.ie.common.UserNotFoundException;
import ir.ac.ut.ece.ie.purchases.PurchaseItem;
import ir.ac.ut.ece.ie.purchases.PurchaseItemRepository;
import ir.ac.ut.ece.ie.purchases.PurchaseRepository;
import ir.ac.ut.ece.ie.reviews.Review;
import ir.ac.ut.ece.ie.testdata.TestDataFactory;
import ir.ac.ut.ece.ie.users.AdminRepository;
import ir.ac.ut.ece.ie.users.User;
import ir.ac.ut.ece.ie.users.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Collections;
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
    private AdminRepository adminRepository;
    @MockitoBean
    private AuthorRepository authorRepository;
    @MockitoBean
    private BookRepository bookRepository;
    @MockitoBean
    private PurchaseRepository purchaseRepository;
    @MockitoBean
    private PurchaseItemRepository purchaseItemRepository;
    @MockitoBean
    private AuthRepository authRepository;
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
    void addBook_notLoggedInAdmin_throwsException() {
        var request = new AddBookRequest();
        when(authorRepository.findByName(any())).thenReturn(Optional.of(new Author()));

        var user = TestDataFactory.sampleAdminUser();

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(adminRepository.findByUsername(any())).thenReturn(Optional.of(user));


        assertThrows(NotLoggedInException.class, () -> bookService.addBook(request));
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
        when(adminRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(user));
        when(authRepository.isLoggedIn(user)).thenReturn(true);

        bookService.addBook(request);

        verify(bookRepository).save(argThat(book ->
                book.getTitle().equals(request.getTitle()) &&
                book.getAuthor().equals(author) &&
                book.getPublisher().equals(request.getPublisher()) &&
                book.getYear().equals(request.getYear()) &&
                book.getGenreNames().equals(request.getGenres()) &&
                book.getPrice().equals(request.getPrice()) &&
                book.getSynopsis().equals(request.getSynopsis()) &&
                book.getContent().equals(request.getContent())
        ));
    }

    @Test
    void getBook_bookNotFound_throwsException() {
        assertThrows(BookNotFoundException.class, () -> bookService.getBook(0L));
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
                .id(1L)
                .title("title")
                .author(author)
                .publisher("publisher")
                .year(2000)
                .genres(Set.of(new Genre("genres")))
                .price(100)
                .synopsis("synopsis")
                .content("content").reviews(Set.of(review1, review2)).build();

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        var bookDto = bookService.getBook(1L);
        assertEquals(book.getTitle(), bookDto.getTitle());
        assertEquals(author.getName(), bookDto.getAuthor());
        assertEquals(book.getPublisher(), bookDto.getPublisher());
        assertEquals(book.getYear(), bookDto.getYear());
        assertEquals(book.getGenreNames(), bookDto.getGenres());
        assertEquals(book.getPrice(), bookDto.getPrice());
        assertEquals(book.getSynopsis(), bookDto.getSynopsis());
        assertEquals(3.5, bookDto.getAverageRating());
    }

    @Test
    void getBookContent_bookNotFound_throwsException() {
        assertThrows(BookNotFoundException.class, () -> bookService.getBookContent(1L));
    }

    @Test
    void getBookContent_userNotLoggedIn_throwsException() {
        when(bookRepository.findById(any())).thenReturn(Optional.of(new Book()));
        assertThrows(NotLoggedInException.class, () -> bookService.getBookContent(1L));
    }

    @Test
    void getBookContent_notPurchasedBook_throwsException() {
        when(bookRepository.findById(any())).thenReturn(Optional.of(new Book()));
        when(authRepository.getAuthenticatedUser()).thenReturn(Optional.of(new User()));
        when(purchaseItemRepository.findPurchaseItems(any(), any())).thenReturn(Collections.emptyList());

        assertThrows(BookNotInStockException.class, () -> bookService.getBookContent(1L));
    }

    @Test
    void getBookContent_expiredBorrowedBook_throwsException() {
        var purchase = mock(PurchaseItem.class);

        when(purchase.hasExpired()).thenReturn(true);
        when(purchase.getIsBorrowed()).thenReturn(true);

        when(bookRepository.findById(any())).thenReturn(Optional.of(new Book()));
        when(authRepository.getAuthenticatedUser()).thenReturn(Optional.of(new User()));
        when(purchaseItemRepository.findPurchaseItems(any(), any())).thenReturn(List.of(purchase));

        assertThrows(BookNotInStockException.class, () -> bookService.getBookContent(1L));
    }

    @Test
    void getBookContent_validInput_returnsContent() {
        String username = "username";
        String title = "title";
        Long id = 2L;

        var user = new User();
        user.setUsername(username);
        var purchase = new PurchaseItem();
        var book = new Book();
        purchase.setBook(book);
        purchase.setIsBorrowed(false);

        book.setContent("content");
        book.setId(id);
        book.setTitle(title);

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(authRepository.getAuthenticatedUser()).thenReturn(Optional.of(user));
        when(purchaseItemRepository.findPurchaseItems(username, title)).thenReturn(List.of(purchase));

        var bookContentDto = bookService.getBookContent(id);

        assertEquals(title, bookContentDto.getTitle());
        assertEquals(book.getContent(), bookContentDto.getContent());
    }

    @Test
    void getBooks_withoutPagination_returnsBooksDto() {
        var query = new SearchQuery();
        var book = TestDataFactory.sampleBook();

        var bookPage = new BookPage(List.of(book), 1);
        when(bookRepository.findByQuery(eq(query), any(), any())).thenReturn(bookPage);

        var bookPageDto = bookService.getBooks(query, null, null);

        var bookDtoList = bookPageDto.getBooks();

        assertEquals(1, bookDtoList.size());

        var bookDto = bookDtoList.get(0);
        assertEquals(book.getTitle(), bookDto.getTitle());
        assertEquals(book.getAuthor().getName(), bookDto.getAuthor());
        assertEquals(book.getPublisher(), bookDto.getPublisher());
        assertEquals(book.getGenreNames(), bookDto.getGenres());
        assertEquals(book.getYear(), bookDto.getYear());
        assertEquals(book.getPrice(), bookDto.getPrice());
        assertEquals(book.getSynopsis(), bookDto.getSynopsis());
        assertEquals(book.getAverageRating(), bookDto.getAverageRating());
    }

    @Test
    void getBooks_withPagination_returnsBooksDto() {
        var query = new SearchQuery();
        var book = TestDataFactory.sampleBook();
        Integer page = 1;
        Integer size = 1;

        var bookPage = new BookPage(List.of(book), 1);
        when(bookRepository.findByQuery(eq(query), eq(page), eq(size))).thenReturn(bookPage);

        var bookPageDto = bookService.getBooks(query, page, size);
        var bookDtoList = bookPageDto.getBooks();

        assertEquals(1, bookDtoList.size());

        var bookDto = bookDtoList.get(0);
        assertEquals(book.getTitle(), bookDto.getTitle());
        assertEquals(book.getAuthor().getName(), bookDto.getAuthor());
        assertEquals(book.getPublisher(), bookDto.getPublisher());
        assertEquals(book.getGenreNames(), bookDto.getGenres());
        assertEquals(book.getYear(), bookDto.getYear());
        assertEquals(book.getPrice(), bookDto.getPrice());
        assertEquals(book.getSynopsis(), bookDto.getSynopsis());
        assertEquals(book.getAverageRating(), bookDto.getAverageRating());
    }
}
