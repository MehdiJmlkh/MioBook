package ir.ac.ut.ece.ie.purchases;

import ir.ac.ut.ece.ie.carts.PurchaseItem;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class PurchaseItemDto {
    private String title;
    private String author;
    private String publisher;
    private Set<String> genres;
    private Integer year;
    private Boolean isBorrowed;
    private Integer borrowDays;
    private Integer price;
    private Integer finalPrice;

    public static PurchaseItemDto fromPurchaseItem(PurchaseItem item) {
        var dto = new PurchaseItemDto();
        var book = item.getBook();
        dto.title = book.getTitle();
        dto.author = book.getAuthor().getName();
        dto.publisher = book.getPublisher();
        dto.genres = book.getGenres();
        dto.year = book.getYear();
        dto.isBorrowed = item.isBorrowed();
        dto.borrowDays = item.getBorrowDays();
        dto.price = book.getPrice();
        dto.finalPrice = item.getPrice();
        return dto;
    }
}
