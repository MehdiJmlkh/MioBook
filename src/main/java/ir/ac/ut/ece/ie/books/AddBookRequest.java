package ir.ac.ut.ece.ie.books;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AddBookRequest {
    private String username;
    private String title;
    private String author;
    private String publisher;
    private Integer year;
    private Integer price;
    private String synopsis;
    private String content;

    @NotEmpty(message = "Genres must not be empty")
    private Set<String> genres;
}
