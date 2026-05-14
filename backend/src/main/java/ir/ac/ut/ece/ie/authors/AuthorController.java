package ir.ac.ut.ece.ie.authors;

import ir.ac.ut.ece.ie.books.BookPageDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public Author getAuthor(@PathVariable(name = "id") Long id) {
        return authorService.getAuthor(id);
    }

    @GetMapping("/{id}/books")
    public BookPageDto getBooksByAuthor(@PathVariable(name = "id") Long id,
                                        @RequestParam(name = "page") Integer page,
                                        @RequestParam(name = "size") Integer size) {
        return authorService.getBooksByAuthor(id, page, size);
    }

    @PostMapping
    public ResponseEntity<Author> addAuthor(@Valid @RequestBody AddAuthorRequest request) {
        System.out.println(request);
        var author = authorService.addAuthor(request);

        return ResponseEntity.ok(author);
    }

    @ExceptionHandler(AuthorNameAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleAuthorNameAlreadyExistsException() {
        return ResponseEntity.badRequest()
                .body(Map.of("name", "Author already exists."));
    }
}
