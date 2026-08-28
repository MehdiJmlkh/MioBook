package ir.ac.ut.ece.ie.authors;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AuthorDto {
    private Long id;
    private String name;
    private String penName;
    private String nationality;
    private LocalDate born;
    private LocalDate died;
    private String imageLink;
}
