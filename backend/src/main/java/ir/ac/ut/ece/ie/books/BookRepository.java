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


    public List<Book> findByQuery(SearchQuery query, Integer page, Integer size) {
        String title = query.getTitle();
        String author = query.getAuthor();
        String genre = query.getGenre();
        Integer year = query.getYear();

        var filteredBooks = books.stream()
                .filter(book -> title == null || book.getTitle().contains(title))
                .filter(book -> author == null || book.getAuthor().getName().contains(author))
                .filter(book -> genre == null || book.getGenres().contains(genre))
                .filter(book -> (year == null) || book.getYear().equals(year))
                .toList();

        if (page == null || size == null) {
            return filteredBooks;
        }

        int fromPage = Math.min((page - 1) * size, filteredBooks.size());
        int toPage = Math.min(fromPage + size, filteredBooks.size());

        return filteredBooks.subList(fromPage, toPage);
    }

    public List<Book> getTopRated(int limit) {
        return books.stream()
                .sorted(Comparator.comparing(Book::getAverageRating).reversed())
                .limit(limit)
                .toList();
    }

    public List<Book> getNewReleases(int limit) {
        return books.stream()
                .sorted(Comparator.comparing(Book::getYear).reversed())
                .limit(limit)
                .toList();
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

    public List<String> getAllGenres() {
        return books.stream()
                .map(Book::getGenres)
                .flatMap(Set::stream)
                .distinct()
                .toList();
    }
}
