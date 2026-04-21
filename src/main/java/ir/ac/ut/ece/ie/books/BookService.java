package ir.ac.ut.ece.ie.books;

import ir.ac.ut.ece.ie.auth.AuthRepository;
import ir.ac.ut.ece.ie.auth.NotLoggedInException;
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
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.lang.reflect.Parameter;
import java.util.LinkedHashSet;
import java.util.List;

@AllArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;
    private final AuthRepository authRepository;
    private final PurchaseRepository purchaseRepository;
    private final BookMapper bookMapper;

    public BookDto getBook(String title) {
        var book = bookRepository.findByTitle(title)
                .orElseThrow(BookNotFoundException::new);
        return bookMapper.toDto(book);
    }

    public BookContentDto getBookContent(String username, String title) {
        bookRepository.findByTitle(title)
                .orElseThrow(BookNotFoundException::new);

        var user = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        if (!authRepository.isLoggedIn(user)) {
            throw new NotLoggedInException();
        }

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

        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(UserNotFoundException::new);

        if (user.getRole() != Role.ADMIN) {
            throw new NotAdminException();
        }

        if (!authRepository.isLoggedIn(user)) {
            throw new NotLoggedInException();
        }

        var book = bookMapper.toBook(request);
        book.setAuthor(author);
        book.setReviews(new LinkedHashSet<>());

        bookRepository.addBook(book);
        return book;
    }

    public List<BookDto> getBooks(SearchQuery query, Integer page, Integer size) {
        var from = query.getFrom();
        var to = query.getTo();

        if (from != null && to != null && from >= to) {
            throw new InvalidYearRangeException();
        }

        return bookRepository.findByQuery(query, page, size).stream()
                .map(bookMapper::toDto)
                .toList();
    }
}
