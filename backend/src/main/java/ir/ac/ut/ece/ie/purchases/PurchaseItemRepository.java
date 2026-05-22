package ir.ac.ut.ece.ie.purchases;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PurchaseItemRepository extends CrudRepository<PurchaseItem, Long> {
    @Query("select p from PurchaseItem p where p.purchase.user.username = :username and p.book.title = :bookTitle")
    List<PurchaseItem> findPurchaseItems(@Param("username") String username, @Param("bookTitle") String bookTitle);

    @Query(value = """
    select pi.*
    from purchased_items pi
    join purchases p on pi.purchase_id = p.id
    join users u on p.customer_id = u.id
    join books b on pi.book_id = b.id
    where u.username = :username
      and b.title = :bookTitle
      and (
            pi.is_borrowed = false
            or (pi.is_borrowed = true and DATE_ADD(pi.date, INTERVAL pi.borrow_days DAY) >= :now)
          )
    """, nativeQuery = true)
    List<PurchaseItem> findNotExpiredPurchaseItems(
            String username,
            String bookTitle,
            LocalDateTime now
    );

}
