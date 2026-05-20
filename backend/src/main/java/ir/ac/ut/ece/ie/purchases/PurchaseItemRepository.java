package ir.ac.ut.ece.ie.purchases;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PurchaseItemRepository extends CrudRepository<PurchaseItem, Long> {
    @Query("select p from PurchaseItem p where p.purchase.user.username = :username and p.book.title = :bookTitle")
    List<PurchaseItem> findPurchaseItems(@Param("username") String username, @Param("bookTitle") String bookTitle);
}
