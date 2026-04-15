package ir.ac.ut.ece.ie.purchases;

import ir.ac.ut.ece.ie.books.Book;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PurchaseItem {
    private Book book;
    private Boolean isBorrowed;
    private Integer borrowDays;
    private Integer price;
    private LocalDateTime date;


    public boolean hasExpired() {
        if (!isBorrowed) {
            return false;
        }

        LocalDateTime expiryDate = date.plusDays(borrowDays);

        return LocalDateTime.now().isAfter(expiryDate);
    }
}
