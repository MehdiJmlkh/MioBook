package ir.ac.ut.ece.ie.carts;

import ir.ac.ut.ece.ie.users.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartRepository extends CrudRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);

    @Query("select count(ci) from Cart c join c.items ci where c.user = :user")
    Long countCartItems(@Param("user") User user);
}