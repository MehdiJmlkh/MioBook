package ir.ac.ut.ece.ie.authors;

import ir.ac.ut.ece.ie.users.Response;
import ir.ac.ut.ece.ie.users.Role;
import ir.ac.ut.ece.ie.users.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;
    private final AuthorMapper authorMapper;

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
