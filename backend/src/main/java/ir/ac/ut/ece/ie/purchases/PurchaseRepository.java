package ir.ac.ut.ece.ie.purchases;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PurchaseRepository extends CrudRepository<Purchase, Long> {
    @Query("select p from Purchase p where p.user.username = :username")
    List<Purchase> findByUsername(@Param("username") String username);
}
