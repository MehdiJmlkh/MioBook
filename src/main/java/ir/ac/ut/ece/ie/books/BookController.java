package ir.ac.ut.ece.ie.books;

import ir.ac.ut.ece.ie.common.NotAdminException;
import ir.ac.ut.ece.ie.common.UserNotFoundException;
import ir.ac.ut.ece.ie.common.ErrorDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

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

    @ExceptionHandler(AuthorNotExistsException.class)
    public ResponseEntity<ErrorDto> handleAuthorNotExistsException() {
        return ResponseEntity.badRequest()
                .body(new ErrorDto("The author does not exists."));
    }
}
