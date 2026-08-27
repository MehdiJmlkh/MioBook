package ir.ac.ut.ece.ie.books;

import ir.ac.ut.ece.ie.auth.AuthService;
import ir.ac.ut.ece.ie.authors.AuthorRepository;
import ir.ac.ut.ece.ie.purchases.PurchaseItemRepository;
import ir.ac.ut.ece.ie.common.AuthorNotFoundException;
import ir.ac.ut.ece.ie.common.BookNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final AuthService authService;
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

        var user = authService.me();

        var purchaseItems = purchaseItemRepository.findNotExpiredPurchaseItems(
                user.getUsername(), book.getTitle(), LocalDateTime.now());

        if (purchaseItems.isEmpty()) {
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

        var genres = request.getGenres().stream()
                .map(name -> genreRepository.findByName(name)
                        .orElseGet(() -> new Genre(name)))
                .collect(Collectors.toSet());

        var book = bookMapper.toBook(request);
        book.setAuthor(author);
        book.setGenres(genres);
        book.setReviews(new LinkedHashSet<>());
        book.setAdmin(authService.currentAdmin());

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

        Sort.Direction direction = query.getOrder() == SortOrder.Ascending
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

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
        return genreRepository.findAllNames();
    }

    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }
}
