package ir.ac.ut.ece.ie.books;

import org.springframework.stereotype.Repository;

import java.util.LinkedHashSet;
import java.util.Set;

@Repository
public class GenreRepository {
    private final Set<Genre> genres = new LinkedHashSet<>();
    private Long lastId = 0L;

    public Genre addGenreByName(String name) {
        var genre = new Genre(name);
        genre.setId(lastId++);
        genres.add(genre);
        return genre;
    }

    public Genre getGenreByName(String name) {
        var genre = genres.stream()
                .filter(g -> g.getName().equals(name))
                .findFirst()
                .orElse(null);

        if (genre != null) {
            return genre;
        }
        return addGenreByName(name);
    }

}
