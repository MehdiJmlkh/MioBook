package ir.ac.ut.ece.ie.purchases;

import lombok.Data;

import java.util.Set;

@Data
public class PurchasedBookDto {
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private Set<String> genres;
    private Integer year;
    private Integer price;
    private Boolean isBorrowed;
    private String expiredDate;
}
