package ir.ac.ut.ece.ie.books;

import ir.ac.ut.ece.ie.authors.NotAdminException;
import ir.ac.ut.ece.ie.authors.UserNotFoundException;
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
    public ResponseEntity<Void> addBook(@RequestBody AddBookRequest request) {
        bookService.addBook(request);
        return ResponseEntity.ok().build();
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

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUserNotFoundException() {
        return ResponseEntity.badRequest()
                .body(new ErrorDto("User not found."));
    }

    @ExceptionHandler(NotAdminException.class)
    public ResponseEntity<ErrorDto> handleNotAdminException() {
        return ResponseEntity.badRequest()
                .body(new ErrorDto("Only admins can add a book."));
    }
}
