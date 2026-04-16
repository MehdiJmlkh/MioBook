package ir.ac.ut.ece.ie.books;

import ir.ac.ut.ece.ie.authors.Author;
import ir.ac.ut.ece.ie.reviews.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Book {
    private String title;
    private Author author;
    private String publisher;
    private Integer year;
    private Set<String> genres;
    private Integer price;
    private String synopsis;
    private String content;

    private Set<Review> reviews = new LinkedHashSet<>();

    public float getAverageRating() {
        if (reviews.isEmpty()) {
            return 0;
        }
        return (float) reviews.stream()
                .map(Review::getRate)
                .reduce(Integer::sum)
                .orElse(0) / reviews.size();
    }

    public boolean publishedInRange(Integer from, Integer to) {
        return (from <= year) && (year <= to);
    }
}
