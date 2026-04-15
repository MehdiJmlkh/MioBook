package ir.ac.ut.ece.ie.books;

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
    private Set<String> genres;
}
