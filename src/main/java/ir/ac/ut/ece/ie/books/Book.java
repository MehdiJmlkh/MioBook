package ir.ac.ut.ece.ie.books;

import ir.ac.ut.ece.ie.authors.Author;
import lombok.Data;

import java.util.Set;

@Data
public class Book {
    private String title;
    private String author;
    private String publisher;
    private Integer year;
    private Set<String> genres;
    private Integer price;
    private String synopsis;
    private String content;
}
