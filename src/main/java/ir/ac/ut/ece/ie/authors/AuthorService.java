package ir.ac.ut.ece.ie.authors;

import ir.ac.ut.ece.ie.common.AuthorNotFoundException;
import ir.ac.ut.ece.ie.common.NotAdminException;
import ir.ac.ut.ece.ie.common.UserNotFoundException;
import ir.ac.ut.ece.ie.users.Role;
import ir.ac.ut.ece.ie.users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;
    private final AuthorMapper authorMapper;

    public Author getAuthor(String name) {
        return authorRepository.findByName(name)
                .orElseThrow(AuthorNotFoundException::new);
    }

    public Author addAuthor(AddAuthorRequest request) {
        if (authorRepository.findByName(request.getName()).isPresent()) {
            throw new AuthorNameAlreadyExistsException();
        }

        var user = userRepository.findByUsername(request.getUsername()).orElse(null);
        if (user == null) {
            throw new UserNotFoundException();
        }

        if (user.getRole() != Role.ADMIN) {
            throw new NotAdminException();
        }

        var author = authorMapper.toAuthor(request);
        authorRepository.addAuthor(author);
        return author;
    }
}
