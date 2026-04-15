package ir.ac.ut.ece.ie.books;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
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


    public List<Book> findByTitleLikes(String title) {
        return books.stream()
                .filter(book -> book.getTitle().contains(title))
                .toList();
    }

    public List<Book> findByAuthorLikes(String author) {
        return books.stream()
                .filter(book -> book.getAuthor().getName().contains(author))
                .toList();
    }

    public List<Book> findByGenre(String genre) {
        return books.stream()
                .filter(book -> book.getGenres().contains(genre))
                .toList();
    }

    public List<Book> findByYear(Integer from, Integer to) {
        return books.stream()
                .filter(book -> book.publishedInRange(from, to))
                .toList();
    }
}
