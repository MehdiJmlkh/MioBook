package ir.ac.ut.ece.ie.books;


import lombok.Data;

import java.util.Set;

@Data
public class BookDto {
    private Long id;
    private Long authorId;
    private String title;
    private String author;
    private String publisher;
    private Set<String> genres;
    private Integer year;
    private Integer price;
    private String synopsis;
    private float averageRating;
    private Integer totalBuys;
}
