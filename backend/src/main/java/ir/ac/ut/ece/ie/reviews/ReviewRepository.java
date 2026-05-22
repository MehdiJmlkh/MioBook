package ir.ac.ut.ece.ie.reviews;

import ir.ac.ut.ece.ie.books.Book;
import ir.ac.ut.ece.ie.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ReviewRepository extends CrudRepository<Review, Long> {
    Optional<Review> findByUserAndBook(User user, Book book);
    Page<Review> findByBook(Book book, Pageable pageable);
    int countByBook(Book book);
}
