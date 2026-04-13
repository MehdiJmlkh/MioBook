package ir.ac.ut.ece.ie.books;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public class BookRepository {
    private Set<Book> books = new LinkedHashSet<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public Optional<Book> findByTitle(String title) {
        return books.stream()
                .filter(book -> book.getTitle().equals(title))
                .findFirst();
    }
}
