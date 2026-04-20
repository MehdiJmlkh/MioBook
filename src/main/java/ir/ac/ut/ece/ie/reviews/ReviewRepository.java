package ir.ac.ut.ece.ie.reviews;

import ir.ac.ut.ece.ie.books.Book;
import ir.ac.ut.ece.ie.users.User;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class ReviewRepository {
    private final Set<Review> reviews = new LinkedHashSet<>();

    public Optional<Review> findByUserAndBook(User user, Book book) {
        return reviews.stream()
                .filter(review -> review.getUser().equals(user))
                .filter(review -> review.getBook().equals(book))
                .findFirst();
    }

    public void addReview(Review review) {
        reviews.add(review);
    }

    public void deleteReview(Review review) {
        reviews.remove(review);
    }
}
