package ir.ac.ut.ece.ie.reviews;

import ir.ac.ut.ece.ie.auth.AuthService;
import ir.ac.ut.ece.ie.books.BookNotInStockException;
import ir.ac.ut.ece.ie.books.BookRepository;
import ir.ac.ut.ece.ie.common.BookNotFoundException;
import ir.ac.ut.ece.ie.common.UserNotFoundException;
import ir.ac.ut.ece.ie.purchases.PurchaseItemRepository;
import ir.ac.ut.ece.ie.users.CustomerRepository;
import ir.ac.ut.ece.ie.users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class ReviewService {
    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;
    private final PurchaseItemRepository purchaseItemRepository;
    private final ReviewMapper reviewMapper;
    private final AuthService authService;

    public ReviewListDto getAllReviews(Long bookId, Integer page, Integer size) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(BookNotFoundException::new);

        var reviews = reviewRepository.findByBook(book, PageRequest.of(page - 1, size))
                .getContent().stream()
                .map(reviewMapper::toDto)
                .toList();

        var reviewListDto = new ReviewListDto();
        reviewListDto.setTitle(book.getTitle());
        reviewListDto.setReviews(reviews);
        reviewListDto.setAverageRating(book.getAverageRating());
        reviewListDto.setTotalReviews(reviewRepository.countByBook(book));

        return reviewListDto;
    }

    public ReviewDto addReview(AddReviewRequest request) {
        var bookTitle = request.getTitle();

        var user = authService.me();

        var book = bookRepository.findByTitle(bookTitle)
                .orElseThrow(BookNotFoundException::new);

        var purchaseItems = purchaseItemRepository.findNotExpiredPurchaseItems(
                user.getUsername(), bookTitle, LocalDateTime.now());

        if (purchaseItems.isEmpty()) {
            throw new BookNotInStockException();
        }

        var review = reviewRepository.findByUserAndBook(user, book)
                .orElse(new Review());

        review.setUser(user);
        review.setBook(book);
        review.setRate(request.getRate());
        review.setComment(request.getComment());
        review.setDate(LocalDate.now());

        reviewRepository.save(review);
        return reviewMapper.toDto(review);
    }
}
