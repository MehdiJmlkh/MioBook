package ir.ac.ut.ece.ie.purchases;

import ir.ac.ut.ece.ie.books.Book;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "purchased_items")
public class PurchaseItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "is_borrowed")
    private Boolean isBorrowed;

    @Column(name = "borrow_days")
    private Integer borrowDays;

    @Column(name = "price")
    private Integer price;

    @Column(name = "date")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    public boolean hasExpired() {
        if (!isBorrowed) {
            return false;
        }

        LocalDateTime expiryDate = date.plusDays(borrowDays);

        return LocalDateTime.now().isAfter(expiryDate);
    }
}
