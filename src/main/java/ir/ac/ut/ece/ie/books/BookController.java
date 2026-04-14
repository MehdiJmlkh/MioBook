package ir.ac.ut.ece.ie.books;

import ir.ac.ut.ece.ie.common.AuthorNotFoundException;
import ir.ac.ut.ece.ie.common.ErrorDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @GetMapping("/{title}")
    public BookDto getBook(@PathVariable(name = "title") String title) {
        return bookService.getBook(title);
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
