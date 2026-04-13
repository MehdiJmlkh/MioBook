package ir.ac.ut.ece.ie.authors;

import ir.ac.ut.ece.ie.users.Response;
import ir.ac.ut.ece.ie.users.UserRepository;
import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;

@AllArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository = new AuthorRepository();
    private final UserRepository userRepository = new UserRepository();
    private final AuthorMapper authorMapper = Mappers.getMapper(AuthorMapper.class);

    public Response addAuthor(AddAuthorRequest request) {
        var author = authorRepository.findByName(request.getName()).orElse(null);
        if (author != null) {
            return Response.failed("An author with this name already exists.");
        }

        var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        if (!user.getRole().equals("admin")) {
            return Response.failed("Only admins can add an author.");
        }

        authorRepository.addAuthor(authorMapper.toAuthor(request));

        return Response.ok("Author added successfully.");
    }
}
