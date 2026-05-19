package ir.ac.ut.ece.ie.books;

import ir.ac.ut.ece.ie.authors.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String title);


    default BookPage findByQuery(SearchQuery query, Integer page, Integer size) {
        String title = query.getTitle();
        String author = query.getAuthor();
        String genre = query.getGenre();
        Integer year = query.getYear();

        Comparator<Book> comparator = query.getSortBy() == SortType.Reviews
                ? Comparator.comparing(Book::getReviewsCount)
                : Comparator.comparing(Book::getAverageRating);

        if (query.getOrder() == SortOrder.Descending) {
            comparator = comparator.reversed();
        }
        var books = findAll();
        var filteredBooks = books.stream()
                .filter(book -> title == null || book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .filter(book -> author == null || book.getAuthor().getName().toLowerCase().contains(author.toLowerCase()))
                .filter(book -> genre == null || book.getGenreNames().stream()
                                        .anyMatch(g -> g.toLowerCase().contains(genre.toLowerCase()))
                )
                .filter(book -> (year == null) || book.getYear().equals(year))
                .sorted(comparator)
                .toList();

        if (page == null || size == null) {
            return new BookPage(filteredBooks, filteredBooks.size());
        }

        int fromPage = Math.min((page - 1) * size, filteredBooks.size());
        int toPage = Math.min(fromPage + size, filteredBooks.size());

        return new BookPage(filteredBooks.subList(fromPage, toPage), filteredBooks.size());
    }

    default List<Book> getTopRated(int limit) {
        var books = findAll();
        return books.stream()
                .sorted(Comparator.comparing(Book::getAverageRating).reversed())
                .limit(limit)
                .toList();
    }

    default List<Book> getNewReleases(int limit) {
        var books = findAll();
        return books.stream()
                .sorted(Comparator.comparing(Book::getYear).reversed())
                .limit(limit)
                .toList();
    }

    default List<Book> findByTitleLikes(String title) {
        var books = findAll();
        return books.stream()
                .filter(book -> book.getTitle().contains(title))
                .toList();
    }

    default List<Book> findByAuthorLikes(String author) {
        var books = findAll();
        return books.stream()
                .filter(book -> book.getAuthor().getName().contains(author))
                .toList();
    }

    default List<Book> findByGenre(String genre) {
        var books = findAll();
        return books.stream()
                .filter(book -> book.getGenres().contains(genre))
                .toList();
    }

    default List<Book> findByYear(Integer from, Integer to) {
        var books = findAll();
        return books.stream()
                .filter(book -> book.publishedInRange(from, to))
                .toList();
    }

    default BookPage findByAuthor(Author author, Integer page, Integer size) {
        var books = findAll();
        var filteredBooks = books.stream()
                .filter(book -> book.getAuthor().equals(author))
                .toList();

        if (page == null || size == null) {
            return new BookPage(filteredBooks, filteredBooks.size());
        }

        int fromPage = Math.min((page - 1) * size, filteredBooks.size());
        int toPage = Math.min(fromPage + size, filteredBooks.size());

        return new BookPage(filteredBooks.subList(fromPage, toPage), filteredBooks.size());
    }
}
