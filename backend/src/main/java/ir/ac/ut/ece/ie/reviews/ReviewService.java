package ir.ac.ut.ece.ie.reviews;

import ir.ac.ut.ece.ie.auth.AuthRepository;
import ir.ac.ut.ece.ie.auth.NotLoggedInException;
import ir.ac.ut.ece.ie.books.BookNotInStockException;
import ir.ac.ut.ece.ie.books.BookRepository;
import ir.ac.ut.ece.ie.common.BookNotFoundException;
import ir.ac.ut.ece.ie.common.NotCustomerException;
import ir.ac.ut.ece.ie.common.UserNotFoundException;
import ir.ac.ut.ece.ie.purchases.PurchaseRepository;
import ir.ac.ut.ece.ie.users.Role;
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
    private final PurchaseRepository purchaseRepository;
    private final AuthRepository authRepository;
    private final ReviewMapper reviewMapper;

    public ReviewListDto getAllReviews(String title, Integer page, Integer size) {
        var book = bookRepository.findByTitle(title)
                .orElseThrow(BookNotFoundException::new);

        var reviews = reviewRepository.findByBook(book, page, size).stream()
                .map(reviewMapper::toDto)
                .toList();

        var reviewListDto = new ReviewListDto();
        reviewListDto.setTitle(title);
        reviewListDto.setReviews(reviews);
        reviewListDto.setAverageRating(book.getAverageRating());
        reviewListDto.setTotalReviews(reviewRepository.getCountByBook(book));

        return reviewListDto;
    }

    public void addReview(AddReviewRequest request) {
        var username = request.getUsername();
        var bookTitle = request.getTitle();

        var user = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        if (user.getRole() != Role.CUSTOMER) {
            throw new NotCustomerException();
        }

        if (!authRepository.isLoggedIn(user)) {
            throw new NotLoggedInException();
        }

        var book = bookRepository.findByTitle(bookTitle)
                .orElseThrow(BookNotFoundException::new);

        var purchase = purchaseRepository.findByUsernameAndTitle(username, bookTitle)
                .orElseThrow(BookNotInStockException::new);

        if (purchase.hasExpired()) {
            throw new BookNotInStockException();
        }

        reviewRepository.findByUserAndBook(user, book)
                .ifPresent(reviewRepository::deleteReview);

        var review = Review.builder()
                .user(user)
                .book(book)
                .rate(request.getRate())
                .comment(request.getComment())
                .date(LocalDate.now())
                .build();

        book.getReviews().add(review);
        reviewRepository.addReview(review);
    }
}
