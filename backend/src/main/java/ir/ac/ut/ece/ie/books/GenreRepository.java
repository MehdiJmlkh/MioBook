package ir.ac.ut.ece.ie.books;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> findByName(String name);

    default List<String> getAllGenreNames() {
        var genres = findAll();
        return genres.stream()
                .map(Genre::getName)
                .toList();
    }
}
