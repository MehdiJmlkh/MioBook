package ir.ac.ut.ece.ie.books;

import org.springframework.data.jpa.domain.Specification;

public class BookSpec {
    public static Specification<Book> hasTitle(String title) {
        return (root, query, cb) ->
                cb.like(
                        cb.lower(root.get("title")),
                        "%" + title.toLowerCase() + "%"
                );
    }

    public static Specification<Book> hasAuthor(String name) {
        return (root, query, cb) ->
                cb.like(
                        cb.lower(root.get("author").get("name")),
                        "%" + name.toLowerCase() + "%"
                );
    }

    public static Specification<Book> hasGenre(String genre) {
        return (root, query, cb) -> {
            assert query != null;
            query.distinct(true);

            var genreJoin = root.join("genres");

            return cb.like(
                    cb.lower(genreJoin.get("name")),
                    "%" + genre.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Book> hasYear(Integer year) {
        return (root, query, cb) ->
                cb.equal(root.get("year"), year);
    }
}
