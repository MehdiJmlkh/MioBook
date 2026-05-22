package ir.ac.ut.ece.ie.books;

import ir.ac.ut.ece.ie.authors.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    Optional<Book> findByTitle(String title);
    Page<Book> findByAuthor(Author author, Pageable pageable);

    @Query(value = "SELECT * FROM books ORDER BY average_rating DESC LIMIT :limit", nativeQuery = true)
    List<Book> getTopRated(@Param("limit") int limit);

    @Query(value = "SELECT * FROM books ORDER BY year DESC LIMIT :limit", nativeQuery = true)
    List<Book> getNewReleases(@Param("limit") int limit);
}
