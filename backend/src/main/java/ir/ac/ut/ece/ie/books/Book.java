package ir.ac.ut.ece.ie.books;

import ir.ac.ut.ece.ie.authors.Author;
import ir.ac.ut.ece.ie.reviews.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Book {
    private Long id;
    private String title;
    private Author author;
    private String publisher;
    private Integer year;
    private Integer price;
    private String synopsis;
    private String content;
    private int totalBuys = 0;

    private Set<Genre> genres = new LinkedHashSet<>();
    private Set<Review> reviews = new LinkedHashSet<>();

    public Set<String> getGenreNames() {
        return genres.stream()
                .map(Genre::getName)
                .collect(Collectors.toSet());
    }

    public void IncrementBuys() {
        totalBuys++;
    }

    public int getReviewsCount() {
        return reviews.size();
    }

    public float getAverageRating() {
        if (reviews.isEmpty()) {
            return 0;
        }
        float rating = (float) reviews.stream()
                .map(Review::getRate)
                .reduce(Integer::sum)
                .orElse(0) / reviews.size();

        return (float) (Math.round(rating * 2.0) / 2.0);
    }

    public boolean publishedInRange(Integer from, Integer to) {
        if (from == null) {
            return year <= to;
        }
        if (to == null) {
            return from <= year;
        }
        return (from <= year) && (year <= to);
    }
}
