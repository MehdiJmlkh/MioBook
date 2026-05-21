package ir.ac.ut.ece.ie.books;

import ir.ac.ut.ece.ie.auth.AuthRepository;
import ir.ac.ut.ece.ie.auth.NotLoggedInException;
import ir.ac.ut.ece.ie.authors.AuthorRepository;
import ir.ac.ut.ece.ie.purchases.PurchaseItemRepository;
import ir.ac.ut.ece.ie.purchases.PurchaseRepository;
import ir.ac.ut.ece.ie.common.AuthorNotFoundException;
import ir.ac.ut.ece.ie.common.BookNotFoundException;
import ir.ac.ut.ece.ie.common.NotAdminException;
import ir.ac.ut.ece.ie.common.UserNotFoundException;
import ir.ac.ut.ece.ie.users.AdminRepository;
import ir.ac.ut.ece.ie.users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
    private final AdminRepository adminRepository;
    private final AuthRepository authRepository;
    private final PurchaseItemRepository purchaseItemRepository;
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

        var purchaseItems = purchaseItemRepository.findPurchaseItems(user.getUsername(), book.getTitle());
        if (purchaseItems.isEmpty()) {
            throw new BookNotInStockException();
        }
        purchaseItems.forEach(purchaseItem -> {
            if (purchaseItem.hasExpired()) {
                throw new BookNotInStockException();
            }
        });

        return bookMapper.toContentDto(book);
    }

    public BookDto addBook(AddBookRequest request) {
        if (bookRepository.findByTitle(request.getTitle()).isPresent()) {
            throw new BookTitleAlreadyExistsException();
        }

        var author = authorRepository.findByName(request.getAuthor())
                .orElseThrow(AuthorNotFoundException::new);

        userRepository.findByUsername(request.getUsername())
                .orElseThrow(UserNotFoundException::new);

        var user = adminRepository.findByUsername(request.getUsername())
                .orElseThrow(NotAdminException::new);

        if (!authRepository.isLoggedIn(user)) {
            throw new NotLoggedInException();
        }

        var genres = request.getGenres().stream()
                .map(name -> genreRepository.findByName(name)
                        .orElseGet(() -> new Genre(name)))
                .collect(Collectors.toSet());

        var book = bookMapper.toBook(request);
        book.setAuthor(author);
        book.setGenres(genres);
        book.setReviews(new LinkedHashSet<>());
        book.setAdmin(user);

        bookRepository.save(book);
        return bookMapper.toDto(book);
    }

    public BookPageDto getBooks(SearchQuery query, Integer page, Integer size) {
        Specification<Book> spec = Specification.where(null);
        if (query.getTitle() != null) {
            spec = spec.and(BookSpec.hasTitle(query.getTitle()));
        }
        if (query.getAuthor() != null) {
            spec = spec.and(BookSpec.hasAuthor(query.getAuthor()));
        }
        if (query.getGenre() != null) {
            spec = spec.and(BookSpec.hasGenre(query.getGenre()));
        }
        if (query.getYear() != null) {
            spec = spec.and(BookSpec.hasYear(query.getYear()));
        }

        String sortField = query.getSortBy() == SortType.Reviews
                ? "reviewsCount"
                : "averageRating";

        Sort.Direction direction = query.getOrder() == SortOrder.Descending
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Sort sort = Sort.by(direction, sortField);

        var bookPage = bookRepository.findAll(spec, PageRequest.of(page - 1, size, sort));

        var books = bookPage.getContent().stream()
                .map(bookMapper::toDto)
                .toList();

        var response = new BookPageDto();
        response.setBooks(books);
        response.setTotalBooks(bookPage.getTotalElements());

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
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }
}
