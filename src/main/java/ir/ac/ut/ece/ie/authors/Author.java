package ir.ac.ut.ece.ie.authors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Author {
    private String name;
    private String penName;
    private String nationality;
    private LocalDate born;
    private LocalDate died;
}
