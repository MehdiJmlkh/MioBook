package ir.ac.ut.ece.ie.purchases;

import lombok.Data;

import java.util.Set;

@Data
public class PurchaseItemDto {
    private Long bookId;
    private Long authorId;
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
        dto.bookId = book.getId();
        dto.authorId = book.getAuthor().getId();
        dto.title = book.getTitle();
        dto.author = book.getAuthor().getName();
        dto.publisher = book.getPublisher();
        dto.genres = book.getGenres();
        dto.year = book.getYear();
        dto.isBorrowed = item.getIsBorrowed();
        dto.borrowDays = item.getBorrowDays();
        dto.price = book.getPrice();
        dto.finalPrice = item.getPrice();
        return dto;
    }
}
