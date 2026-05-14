package ir.ac.ut.ece.ie.books;

import ir.ac.ut.ece.ie.authors.Author;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class BookRepository {
    private Set<Book> books = new LinkedHashSet<>();
    private Long lastId = 0L;

    public void addBook(Book book) {
        book.setId(lastId++);
        books.add(book);
    }

    public Optional<Book> findById(Long id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst();
    }

    public Optional<Book> findByTitle(String title) {
        return books.stream()
                .filter(book -> book.getTitle().equals(title))
                .findFirst();
    }


    public BookPage findByQuery(SearchQuery query, Integer page, Integer size) {
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

        var filteredBooks = books.stream()
                .filter(book -> title == null || book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .filter(book -> author == null || book.getAuthor().getName().toLowerCase().contains(author.toLowerCase()))
                .filter(book -> genre == null || book.getGenres().stream()
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

    public List<Book> getAll() {
        return books.stream().toList();
    }

    public BookPage findByAuthor(Author author, Integer page, Integer size) {
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
