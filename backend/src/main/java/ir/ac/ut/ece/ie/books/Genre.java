package ir.ac.ut.ece.ie.books;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Genre {
    private Long id;
    private String name;

    public Genre(String name) {
        this.name = name;
    }
}
