package ir.ac.ut.ece.ie.authors;

import ir.ac.ut.ece.ie.auth.AuthRepository;
import ir.ac.ut.ece.ie.auth.NotLoggedInException;
import ir.ac.ut.ece.ie.books.BookMapper;
import ir.ac.ut.ece.ie.books.BookPageDto;
import ir.ac.ut.ece.ie.books.BookRepository;
import ir.ac.ut.ece.ie.common.AuthorNotFoundException;
import ir.ac.ut.ece.ie.common.NotAdminException;
import ir.ac.ut.ece.ie.common.UserNotFoundException;
import ir.ac.ut.ece.ie.users.AdminRepository;
import ir.ac.ut.ece.ie.users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final AuthRepository authRepository;
    private final BookRepository bookRepository;
    private final AuthorMapper authorMapper;
    private final BookMapper bookMapper;

    public Author getAuthor(String name) {
        return authorRepository.findByName(name)
                .orElseThrow(AuthorNotFoundException::new);
    }

    public Author getAuthor(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(AuthorNotFoundException::new);
    }

    public AuthorDto addAuthor(AddAuthorRequest request) {
        if (authorRepository.findByName(request.getName()).isPresent()) {
            throw new AuthorNameAlreadyExistsException();
        }

        userRepository.findByUsername(request.getUsername())
                .orElseThrow(UserNotFoundException::new);

        var user = adminRepository.findByUsername(request.getUsername())
                .orElseThrow(NotAdminException::new);

        if (!authRepository.isLoggedIn(user)) {
            throw new NotLoggedInException();
        }

        var author = authorMapper.toAuthor(request);
        author.setAdmin(user);
        authorRepository.save(author);
        return authorMapper.toDto(author);
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public BookPageDto getBooksByAuthor(Long id, Integer page, Integer size) {
        var author = authorRepository.findById(id)
                .orElseThrow(AuthorNotFoundException::new);

        var bookPage = bookRepository.findByAuthor(author, page, size);
        var bookDtoList = bookPage.getBooks().stream()
                .map(bookMapper::toDto)
                .toList();

        return new BookPageDto(bookDtoList, bookPage.getTotalBooks());
    }
}
