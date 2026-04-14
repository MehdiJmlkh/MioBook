package ir.ac.ut.ece.ie.authors;

import ir.ac.ut.ece.ie.common.ErrorDto;
import ir.ac.ut.ece.ie.common.NotAdminException;
import ir.ac.ut.ece.ie.common.UserNotFoundException;
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

    @GetMapping("/{name}")
    public Author getAuthor(@PathVariable(name = "name") String name) {
        return authorService.getAuthor(name);
    }

    @PostMapping("/add")
    public ResponseEntity<Author> addAuthor(@Valid @RequestBody AddAuthorRequest request) {
        var author = authorService.addAuthor(request);

        return ResponseEntity.ok(author);
    }

    @ExceptionHandler(AuthorNameAlreadyExistsException.class)
    public ResponseEntity<ErrorDto> handleAuthorNameAlreadyExistsException() {
        return ResponseEntity.badRequest()
                .body(new ErrorDto("An author with this name already exists."));
    }
}
