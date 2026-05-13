package ir.ac.ut.ece.ie.reviews;

import ir.ac.ut.ece.ie.auth.AuthRepository;
import ir.ac.ut.ece.ie.auth.NotLoggedInException;
import ir.ac.ut.ece.ie.books.Book;
import ir.ac.ut.ece.ie.books.BookNotInStockException;
import ir.ac.ut.ece.ie.books.BookRepository;
import ir.ac.ut.ece.ie.carts.CartItem;
import ir.ac.ut.ece.ie.common.BookNotFoundException;
import ir.ac.ut.ece.ie.common.NotCustomerException;
import ir.ac.ut.ece.ie.common.UserNotFoundException;
import ir.ac.ut.ece.ie.purchases.PurchaseItem;
import ir.ac.ut.ece.ie.purchases.PurchaseRepository;
import ir.ac.ut.ece.ie.testdata.TestDataFactory;
import ir.ac.ut.ece.ie.users.Role;
import ir.ac.ut.ece.ie.users.User;
import ir.ac.ut.ece.ie.users.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ReviewServiceTest {
    @MockitoBean
    private UserRepository userRepository;
    @MockitoBean
    private BookRepository bookRepository;
    @MockitoBean
    private ReviewRepository reviewRepository;
    @MockitoBean
    private PurchaseRepository purchaseRepository;
    @MockitoBean
    private AuthRepository authRepository;
    @Autowired
    private ReviewService reviewService;

    @Test
    void addReview_userNotFound_throwsException() {
        var request = new AddReviewRequest();
        assertThrows(UserNotFoundException.class, () -> reviewService.addReview(request));
    }

    @Test
    void addReview_notCustomerUser_throwsException() {
        var request = new AddReviewRequest();
        var user = TestDataFactory.sampleAdminUser();

        when(bookRepository.findByTitle(any())).thenReturn(Optional.of(new Book()));
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        assertThrows(NotCustomerException.class, () -> reviewService.addReview(request));
    }

    @Test
    void addReview_userNotLoggedIn_throwsException() {
        var request = new AddReviewRequest();
        var user = TestDataFactory.sampleCustomerUser();

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));

        assertThrows(NotLoggedInException.class, () -> reviewService.addReview(request));
    }

    @Test
    void addReview_bookNotFound_throwsException() {
        var request = new AddReviewRequest();
        var user = TestDataFactory.sampleCustomerUser();

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(authRepository.isLoggedIn(any())).thenReturn(true);

        assertThrows(BookNotFoundException.class, () -> reviewService.addReview(request));
    }

    @Test
    void addReview_bookNotBought_throwsException() {
        var request = new AddReviewRequest();
        var user = TestDataFactory.sampleCustomerUser();

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(authRepository.isLoggedIn(any())).thenReturn(true);
        when(bookRepository.findByTitle(any())).thenReturn(Optional.of(new Book()));

        assertThrows(BookNotInStockException.class, () -> reviewService.addReview(request));
    }

    @Test
    void addReview_borrowedBookHasExpired_throwsException() {
        var request = new AddReviewRequest();
        var user = TestDataFactory.sampleCustomerUser();
        var book = new Book();

        var purchaseItem = mock(PurchaseItem.class);

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(authRepository.isLoggedIn(any())).thenReturn(true);
        when(bookRepository.findByTitle(any())).thenReturn(Optional.of(book));
        when(purchaseRepository.findByUsernameAndTitle(any(), any())).thenReturn(Optional.of(purchaseItem));
        when(purchaseItem.hasExpired()).thenReturn(true);

        assertThrows(BookNotInStockException.class, () -> reviewService.addReview(request));
    }

    @Test
    void addReview_reviewedBefore_removesPreviousReview() {
        var request = new AddReviewRequest();
        var user = TestDataFactory.sampleCustomerUser();
        request.setUsername(user.getUsername());

        var book = new Book();

        var purchaseItem = mock(PurchaseItem.class);
        var previousReview = new Review();

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(authRepository.isLoggedIn(any())).thenReturn(true);
        when(bookRepository.findByTitle(any())).thenReturn(Optional.of(book));
        when(purchaseRepository.findByUsernameAndTitle(any(), any())).thenReturn(Optional.of(purchaseItem));
        when(purchaseItem.hasExpired()).thenReturn(false);
        when(reviewRepository.findByUserAndBook(user, book)).thenReturn(Optional.of(previousReview));

        reviewService.addReview(request);

        verify(reviewRepository).deleteReview(previousReview);
    }

    @Test
    void addReview_firstReview_addsReview() {
        var user = TestDataFactory.sampleCustomerUser();
        var book = new Book();
        book.setTitle("title");

        var request = AddReviewRequest.builder()
                .username(user.getUsername())
                .title(book.getTitle())
                .rate(4)
                .comment("comment").build();


        var purchaseItem = mock(PurchaseItem.class);


        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(user));
        when(authRepository.isLoggedIn(any())).thenReturn(true);
        when(bookRepository.findByTitle(request.getTitle())).thenReturn(Optional.of(book));
        when(purchaseRepository.findByUsernameAndTitle(user.getUsername(), book.getTitle())).thenReturn(Optional.of(purchaseItem));
        when(purchaseItem.hasExpired()).thenReturn(false);

        var before = LocalDate.now();
        reviewService.addReview(request);
        var after = LocalDate.now();


        var captor = ArgumentCaptor.forClass(Review.class);
        verify(reviewRepository).addReview(captor.capture());
        var review = captor.getValue();

        assertEquals(request.getRate(), review.getRate());
        assertEquals(request.getComment(), review.getComment());
        assertEquals(book, review.getBook());
        assertEquals(user, review.getUser());
        assertFalse(review.getDate().isBefore(before));
        assertFalse(review.getDate().isAfter(after));

        assertEquals(1, book.getReviews().size());
    }

    @Test
    void getAllReviews_bookNotFound_throwsException() {
        assertThrows(BookNotFoundException.class, () -> reviewService.getAllReviews(1L, null, null));
    }

    @Test
    void getAllReviews_withoutPagination_returnsReviewListDto() {
        var book = new Book();

        var user1 = User.builder().username("user1").build();
        var review1 = Review.builder()
                .rate(3)
                .comment("comment1")
                .date(LocalDate.of(2001, 1,1))
                .book(book)
                .user(user1)
                .build();

        var user2 = User.builder().username("user2").build();
        var review2 = Review.builder()
                .rate(4)
                .comment("comment2")
                .date(LocalDate.of(2002, 2,2))
                .book(book)
                .user(user2)
                .build();

        book.getReviews().add(review1);
        book.getReviews().add(review2);

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(reviewRepository.findByBook(any(), any(), any())).thenReturn(List.of(review1, review2));

        var reviewListDto = reviewService.getAllReviews(book.getId(), null, null);

        assertEquals(book.getTitle(), reviewListDto.getTitle());
        assertEquals(3.5, reviewListDto.getAverageRating());

        assertEquals(2, reviewListDto.getReviews().size());
        var reviewDto1 = reviewListDto.getReviews().get(0);
        assertEquals(review1.getComment(), reviewDto1.getComment());
        assertEquals(review1.getRate(), reviewDto1.getRate());
        assertEquals(user1.getUsername(), reviewDto1.getUsername());

        var reviewDto2 = reviewListDto.getReviews().get(1);
        assertEquals(review2.getComment(), reviewDto2.getComment());
        assertEquals(review2.getRate(), reviewDto2.getRate());
        assertEquals(user2.getUsername(), reviewDto2.getUsername());
    }

    @Test
    void getAllReviews_withPagination_returnsReviewListDto() {
        var book = new Book();

        var user1 = User.builder().username("user1").build();
        var review1 = Review.builder()
                .rate(3)
                .comment("comment1")
                .date(LocalDate.of(2001, 1,1))
                .book(book)
                .user(user1)
                .build();

        var user2 = User.builder().username("user2").build();
        var review2 = Review.builder()
                .rate(4)
                .comment("comment2")
                .date(LocalDate.of(2002, 2,2))
                .book(book)
                .user(user2)
                .build();

        book.getReviews().add(review1);
        book.getReviews().add(review2);

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(reviewRepository.findByBook(book, 1, 1)).thenReturn(List.of(review2));


        var reviewListDto = reviewService.getAllReviews(book.getId(), 1, 1);

        assertEquals(book.getTitle(), reviewListDto.getTitle());
        assertEquals(3.5, reviewListDto.getAverageRating());

        assertEquals(1, reviewListDto.getReviews().size());
        var reviewDto1 = reviewListDto.getReviews().get(0);
        assertEquals(review2.getComment(), reviewDto1.getComment());
        assertEquals(review2.getRate(), reviewDto1.getRate());
        assertEquals(user2.getUsername(), reviewDto1.getUsername());
    }
}
