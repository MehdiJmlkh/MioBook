package ir.ac.ut.ece.ie.books;

import ir.ac.ut.ece.ie.authors.AuthorRepository;
import ir.ac.ut.ece.ie.purchases.PurchaseRepository;
import ir.ac.ut.ece.ie.common.AuthorNotFoundException;
import ir.ac.ut.ece.ie.common.BookNotFoundException;
import ir.ac.ut.ece.ie.common.NotAdminException;
import ir.ac.ut.ece.ie.common.UserNotFoundException;
import ir.ac.ut.ece.ie.users.Role;
import ir.ac.ut.ece.ie.users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;
    private final PurchaseRepository purchaseRepository;
    private final BookMapper bookMapper;

    public BookDto getBook(String title) {
        var book = bookRepository.findByTitle(title)
                .orElseThrow(BookNotFoundException::new);
        return bookMapper.toDto(book);
    }

    public BookContentDto getBookContent(String username, String title) {
        var book = bookRepository.findByTitle(title)
                .orElseThrow(BookNotFoundException::new);

        var user = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        var purchase =purchaseRepository.findByUsernameAndTitle(username, title)
                .orElseThrow(BookNotInStockException::new);

        if (purchase.getIsBorrowed() && purchase.hasExpired()) {
            throw new BookNotInStockException();
        }

        return bookMapper.toContentDto(purchase.getBook());
    }

    public Book addBook(AddBookRequest request) {
        if (bookRepository.findByTitle(request.getTitle()).isPresent()) {
            throw new BookTitleAlreadyExistsException();
        }

        var author = authorRepository.findByName(request.getAuthor())
                .orElseThrow(AuthorNotFoundException::new);

        var user = userRepository.findByUsername(request.getUsername()).orElse(null);
        if (user == null) {
            throw new UserNotFoundException();
        }
        if (user.getRole() != Role.ADMIN) {
            throw new NotAdminException();
        }

        var book = bookMapper.toBook(request);
        book.setAuthor(author);

        bookRepository.addBook(book);
        return book;
    }

    public List<BookDto> getBooksByTitle(String title) {
        return bookRepository.findByTitleLikes(title).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    public List<BookDto> getBooksByAuthor(String author) {
        return bookRepository.findByAuthorLikes(author).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    public List<BookDto> getBooksByGenre(String genre) {
        return bookRepository.findByGenre(genre).stream()
                .map(bookMapper::toDto)
                .toList();
    }
}
