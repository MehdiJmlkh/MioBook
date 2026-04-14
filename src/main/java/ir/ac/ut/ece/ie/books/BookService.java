package ir.ac.ut.ece.ie.books;

import ir.ac.ut.ece.ie.authors.AuthorRepository;
import ir.ac.ut.ece.ie.common.NotAdminException;
import ir.ac.ut.ece.ie.common.UserNotFoundException;
import ir.ac.ut.ece.ie.users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;
    private final BookMapper bookMapper;

    public void addBook(AddBookRequest request) {
        if (bookRepository.findByTitle(request.getTitle()).isPresent()) {
            throw new BookTitleAlreadyExistsException();
        }

        var author = authorRepository.findByName(request.getAuthor())
                .orElseThrow(AuthorNotExistsException::new);

        var user = userRepository.findByUsername(request.getUsername()).orElse(null);
        if (user == null) {
            throw new UserNotFoundException();
        }
        if (user.getRole().equals("customer")) {
            throw new NotAdminException();
        }

        var book = bookMapper.toBook(request);
        bookRepository.addBook(book);
    }
}
