package ir.ac.ut.ece.ie.authors;

import org.springframework.stereotype.Repository;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class AuthorRepository {
    private final Set<Author> authors = new LinkedHashSet<>();
    private Long lastId = 0L;

    public void addAuthor(Author author) {
        author.setId(lastId++);
        authors.add(author);
    }

    public Optional<Author> findByName(String name) {
        return authors.stream()
                .filter(author -> author.getName().equals(name))
                .findFirst();
    }

    public Optional<Author> findById(Long id) {
        return authors.stream()
                .filter(author -> author.getId().equals(id))
                .findFirst();
    }

    public List<Author> getAll() {
        return authors.stream().toList();
    }
}
