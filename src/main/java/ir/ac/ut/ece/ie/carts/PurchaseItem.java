package ir.ac.ut.ece.ie.carts;

import ir.ac.ut.ece.ie.books.Book;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PurchaseItem {
    private boolean isBorrowed;
    private int borrowDays;
    private Book book;
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
