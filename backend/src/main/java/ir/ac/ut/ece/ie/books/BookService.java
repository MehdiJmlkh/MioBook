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

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;
    private final AuthRepository authRepository;
    private final PurchaseRepository purchaseRepository;
    private final GenreRepository genreRepository;
    private final BookMapper bookMapper;

    public BookDto getBook(Long id) {
        var book = bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
        return bookMapper.toDto(book);
    }

    public BookContentDto getBookContent(Long id) {
        var book = bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);

        var user = authRepository.getAuthenticatedUser()
                .orElseThrow(NotLoggedInException::new);

        var purchase =purchaseRepository.findByUsernameAndTitle(user.getUsername(), book.getTitle())
                .orElseThrow(BookNotInStockException::new);

        if (purchase.getIsBorrowed() && purchase.hasExpired()) {
            throw new BookNotInStockException();
        }

        return bookMapper.toContentDto(book);
    }

    public BookDto addBook(AddBookRequest request) {
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

        var genres = request.getGenres().stream()
                        .map(genreRepository::getGenreByName)
                        .collect(Collectors.toSet());
        var book = bookMapper.toBook(request);
        book.setAuthor(author);
        book.setGenres(genres);
        book.setReviews(new LinkedHashSet<>());

        bookRepository.addBook(book);
        return bookMapper.toDto(book);
    }

    public BookPageDto getBooks(SearchQuery query, Integer page, Integer size) {
        var bookPage = bookRepository.findByQuery(query, page, size);

        var books = bookPage.getBooks().stream()
                .map(bookMapper::toDto)
                .toList();

        var response = new BookPageDto();
        response.setBooks(books);
        response.setTotalBooks(bookPage.getTotalBooks());

        return response;
    }

    public List<BookDto> getTopRated() {
        return bookRepository.getTopRated(5).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    public List<BookDto> getNewReleases() {
        return bookRepository.getNewReleases(5).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    public List<String> getAllGenres() {
        return genreRepository.getAllGenreNames();
    }

    public List<BookDto> getAllBooks() {
        return bookRepository.getAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }
}
