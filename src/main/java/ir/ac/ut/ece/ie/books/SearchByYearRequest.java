package ir.ac.ut.ece.ie.books;

import lombok.Data;

@Data
public class SearchByYearRequest {
    private Integer from;
    private Integer to;
}
