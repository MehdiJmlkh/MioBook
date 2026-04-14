package ir.ac.ut.ece.ie.reviews;

import org.springframework.stereotype.Repository;

import java.util.LinkedHashSet;
import java.util.Set;

@Repository
public class ReviewRepository {
    private final Set<Review> reviews = new LinkedHashSet<>();

    public void addReview(Review review) {
        reviews.add(review);
    }
}
