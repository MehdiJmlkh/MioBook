package ir.ac.ut.ece.ie.reviews;

import ir.ac.ut.ece.ie.books.BookRepository;
import ir.ac.ut.ece.ie.common.BookNotFoundException;
import ir.ac.ut.ece.ie.common.NotCustomerException;
import ir.ac.ut.ece.ie.common.UserNotFoundException;
import ir.ac.ut.ece.ie.users.Role;
import ir.ac.ut.ece.ie.users.User;
import ir.ac.ut.ece.ie.users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@AllArgsConstructor
@Service
public class ReviewService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;

    public void addReview(AddReviewRequest request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(UserNotFoundException::new);

        var book = bookRepository.findByTitle(request.getTitle())
                .orElseThrow(BookNotFoundException::new);

        if (user.getRole() != Role.CUSTOMER) {
            throw new NotCustomerException();
        }

        var review = Review.builder()
                .use(user)
                .book(book)
                .rate(request.getRate())
                .comment(request.getComment())
                .date(LocalDate.now())
                .build();

        reviewRepository.addReview(review);
    }
}
