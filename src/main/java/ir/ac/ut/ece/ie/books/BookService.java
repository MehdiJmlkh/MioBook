package ir.ac.ut.ece.ie.books;

import ir.ac.ut.ece.ie.authors.AuthorRepository;
import ir.ac.ut.ece.ie.users.Response;
import ir.ac.ut.ece.ie.users.User;
import ir.ac.ut.ece.ie.users.UserRepository;
import org.mapstruct.factory.Mappers;

public class BookService {
    private final BookRepository bookRepository = new BookRepository();
    private final AuthorRepository authorRepository = new AuthorRepository();
    private final UserRepository userRepository = new UserRepository();
    private final BookMapper bookMapper = Mappers.getMapper(BookMapper.class);

    public Response addBook(AddBookRequest request) {
        var book = bookRepository.findByTitle(request.getTitle()).orElse(null);
        if (book != null) {
            return Response.failed("A book with this title already exists.");
        }

        var author = authorRepository.findByName(request.getAuthor()).orElse(null);
        if (author == null) {
            return Response.failed("The author does not exists.");
        }

        var user = userRepository.findByUsername(request.getUsername()).orElse(null);
        if (user == null || user.getRole().equals("customer")) {
            return Response.failed("Only admins can add a book.");
        }

        book = bookMapper.toBook(request);
        bookRepository.addBook(book);
        return Response.ok("Book added successfully.");
    }
}
