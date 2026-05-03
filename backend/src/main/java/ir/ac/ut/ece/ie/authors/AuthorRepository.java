package ir.ac.ut.ece.ie.authors;

import org.springframework.stereotype.Repository;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class AuthorRepository {
    private final Set<Author> authors = new LinkedHashSet<>();

    public void addAuthor(Author author) {
        authors.add(author);
    }

    public Optional<Author> findByName(String name) {
        return authors.stream()
                .filter(author -> author.getName().equals(name))
                .findFirst();
    }

    public List<Author> getAll() {
        return authors.stream().toList();
    }
}
