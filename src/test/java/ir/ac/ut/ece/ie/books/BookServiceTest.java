package ir.ac.ut.ece.ie.books;

import ir.ac.ut.ece.ie.authors.Author;
import ir.ac.ut.ece.ie.authors.AuthorRepository;
import ir.ac.ut.ece.ie.common.AuthorNotFoundException;
import ir.ac.ut.ece.ie.common.NotAdminException;
import ir.ac.ut.ece.ie.common.UserNotFoundException;
import ir.ac.ut.ece.ie.users.Role;
import ir.ac.ut.ece.ie.users.User;
import ir.ac.ut.ece.ie.users.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BookServiceTest {

    @MockitoBean
    private UserRepository userRepository;
    @MockitoBean
    private AuthorRepository authorRepository;
    @MockitoBean
    private BookRepository bookRepository;
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

        var user = new User();
        user.setRole(Role.CUSTOMER);
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

        var user = new User();
        user.setRole(Role.ADMIN);
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
}
