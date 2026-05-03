package ir.ac.ut.ece.ie.books;

import lombok.Data;

@Data
public class SearchQuery {
    private String title;
    private String author;
    private String genre;
    private Integer year;
    private SortType sortBy;
    private SortOrder order;
}
