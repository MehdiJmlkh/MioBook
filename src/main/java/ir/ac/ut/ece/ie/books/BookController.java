package ir.ac.ut.ece.ie.books;

import ir.ac.ut.ece.ie.common.ErrorDto;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @GetMapping("/{title}")
    public BookDto getBook(@PathVariable("title") String title) {
        return bookService.getBook(title);
    }

    @GetMapping("/search")
    public List<BookDto> searchBook(
            SearchQuery query,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size
    ) {
        return bookService.getBooks(query, page, size);
    }

    @PostMapping("/{title}/content")
    public BookContentDto getBookContent(
            @PathVariable("title") String title,
            @RequestBody GetBookContentRequest request) {
        return bookService.getBookContent(request.getUsername(), title);
    }

    @PostMapping("/add")
    public ResponseEntity<Book> addBook(@RequestBody AddBookRequest request) {
        var book = bookService.addBook(request);
        return ResponseEntity.ok(book);
    }

    @ExceptionHandler(BookTitleAlreadyExistsException.class)
    public ResponseEntity<ErrorDto> handleBookTitleAlreadyExistsException() {
        return ResponseEntity.badRequest()
                .body(new ErrorDto("A book with this title already exists."));
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
