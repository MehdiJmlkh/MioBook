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

    @GetMapping("/search/title/{title}")
    public List<BookDto> searchBooksByTitle(@PathVariable("title") String title) {
        return bookService.getBooksByTitle(title);
    }

    @GetMapping("/search/author/{author}")
    public List<BookDto> searchBooksByAuthor(@PathVariable("author") String author) {
        return bookService.getBooksByAuthor(author);
    }

    @GetMapping("search/genre/{genre}")
    public List<BookDto> searchBooksByGenre(@PathVariable("genre") String genre) {
        return bookService.getBooksByGenre(genre);
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
}
