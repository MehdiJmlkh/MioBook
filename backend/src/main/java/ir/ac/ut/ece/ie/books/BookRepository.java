package ir.ac.ut.ece.ie.books;

import ir.ac.ut.ece.ie.authors.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.*;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    Optional<Book> findByTitle(String title);
    Page<Book> findByAuthor(Author author, Pageable pageable);
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
}
