package ir.ac.ut.ece.ie.books;

import ir.ac.ut.ece.ie.authors.Author;
import ir.ac.ut.ece.ie.reviews.Review;
import ir.ac.ut.ece.ie.users.Admin;
import jakarta.persistence.*;
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
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "year")
    private Integer year;

    @Column(name = "price")
    private Integer price;

    @Column(name = "synopsis")
    private String synopsis;

    @Column(name = "content")
    private String content;

    @Column(name = "total_buys")
    private int totalBuys = 0;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @ManyToMany
    @JoinTable(
        name = "book_genres",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new LinkedHashSet<>();

    @OneToMany(mappedBy = "book")
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
