package ir.ac.ut.ece.ie.reviews;

import ir.ac.ut.ece.ie.books.Book;
import ir.ac.ut.ece.ie.books.BookRepository;
import ir.ac.ut.ece.ie.carts.CartItem;
import ir.ac.ut.ece.ie.common.BookNotFoundException;
import ir.ac.ut.ece.ie.common.NotCustomerException;
import ir.ac.ut.ece.ie.common.UserNotFoundException;
import ir.ac.ut.ece.ie.users.Role;
import ir.ac.ut.ece.ie.users.User;
import ir.ac.ut.ece.ie.users.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ReviewServiceTest {
    @MockitoBean
    private UserRepository userRepository;
    @MockitoBean
    private BookRepository bookRepository;
    @MockitoBean
    private ReviewRepository reviewRepository;
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
        var user = new User();
        user.setRole(Role.ADMIN);

        when(bookRepository.findByTitle(any())).thenReturn(Optional.of(new Book()));
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        assertThrows(NotCustomerException.class, () -> reviewService.addReview(request));
    }

    @Test
    void addReview_bookNotFound_throwsException() {
        var request = new AddReviewRequest();
        var user = new User();
        user.setRole(Role.CUSTOMER);
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));

        assertThrows(BookNotFoundException.class, () -> reviewService.addReview(request));
    }

    @Test
    void addReview_validInput_addsReview() {
        var request = AddReviewRequest.builder()
                .username("username")
                .title("title")
                .rate(4)
                .comment("comment").build();

        var user = new User();
        user.setRole(Role.CUSTOMER);
        var book = new Book();

        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(user));
        when(bookRepository.findByTitle(request.getTitle())).thenReturn(Optional.of(book));

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
    }

}
