package ir.ac.ut.ece.ie.books;

import ir.ac.ut.ece.ie.common.ErrorDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @GetMapping
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public BookDto getBook(@PathVariable("id") Long id) {
        return bookService.getBook(id);
    }

    @GetMapping("/genres")
    public List<String> getAllGenres() {
        return bookService.getAllGenres();
    }

    @GetMapping("/search")
    public BookPageDto searchBook(
            @ModelAttribute SearchQuery query,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size
    ) {
        System.out.println(query);
        return bookService.getBooks(query, page, size);
    }

    @GetMapping("/{id}/content")
    public BookContentDto getBookContent(@PathVariable("id") Long id) {
        return bookService.getBookContent(id);
    }

    @PostMapping
    public ResponseEntity<BookDto> addBook(@RequestBody AddBookRequest request) {
        var book = bookService.addBook(request);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/top-rated")
    public  List<BookDto> getTopRated() {
        return bookService.getTopRated();
    }

    @GetMapping("/new-releases")
    public  List<BookDto> getNewReleases() {
        return bookService.getNewReleases();
    }

    @ExceptionHandler(BookTitleAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleBookTitleAlreadyExistsException() {
        return ResponseEntity.badRequest()
                .body(Map.of("title", "Book already exists."));
    }

    @ExceptionHandler(InvalidYearRangeException.class)
    public ResponseEntity<ErrorDto> handleInvalidYearRangeException() {
        return ResponseEntity.badRequest()
                .body(new ErrorDto("Start year must be earlier than the end year."));
    }

    @ExceptionHandler(BookNotInStockException.class)
    public ResponseEntity<ErrorDto> handleBookNotInStockException() {
        return ResponseEntity.badRequest()
                .body(new ErrorDto("This is not in your stock"));
    }
}
