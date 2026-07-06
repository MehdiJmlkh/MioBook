package ir.ac.ut.ece.ie.authors;

import ir.ac.ut.ece.ie.auth.AuthService;
import ir.ac.ut.ece.ie.books.BookMapper;
import ir.ac.ut.ece.ie.books.BookPageDto;
import ir.ac.ut.ece.ie.books.BookRepository;
import ir.ac.ut.ece.ie.common.AuthorNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthService authService;
    private final BookRepository bookRepository;
    private final AuthorMapper authorMapper;
    private final BookMapper bookMapper;

    public AuthorDto getAuthor(Long id) {
        var author =  authorRepository.findById(id)
                .orElseThrow(AuthorNotFoundException::new);

        return authorMapper.toDto(author);
    }

    public AuthorDto addAuthor(AddAuthorRequest request) {
        if (authorRepository.findByName(request.getName()).isPresent()) {
            throw new AuthorNameAlreadyExistsException();
        }

        var author = authorMapper.toAuthor(request);
        author.setAdmin(authService.currentAdmin());
        authorRepository.save(author);
        return authorMapper.toDto(author);
    }

    public List<AuthorDto> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(authorMapper::toDto)
                .toList();
    }

    public BookPageDto getBooksByAuthor(Long id, Integer page, Integer size) {
        var author = authorRepository.findById(id)
                .orElseThrow(AuthorNotFoundException::new);

        var bookPage = bookRepository.findByAuthor(author, PageRequest.of(page - 1, size));
        var bookDtoList = bookPage.getContent().stream()
                .map(bookMapper::toDto)
                .toList();

        return new BookPageDto(bookDtoList, bookPage.getTotalElements());
    }
}
