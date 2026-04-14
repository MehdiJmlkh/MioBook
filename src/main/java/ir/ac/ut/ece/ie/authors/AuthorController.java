package ir.ac.ut.ece.ie.authors;

import ir.ac.ut.ece.ie.common.ErrorDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping("/add")
    public ResponseEntity<Void> addAuthor(@Valid @RequestBody AddAuthorRequest request) {
        authorService.addAuthor(request);

        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(AuthorNameAlreadyExistsException.class)
    public ResponseEntity<ErrorDto> handleAuthorNameAlreadyExistsException() {
        return ResponseEntity.badRequest()
                .body(new ErrorDto("An author with this name already exists."));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUserNotFoundException() {
        return ResponseEntity.badRequest()
                .body(new ErrorDto("User not found."));
    }

    @ExceptionHandler(NotAdminException.class)
    public ResponseEntity<ErrorDto> handleNotAdminException() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorDto("Only admins can add authors."));
    }
}
