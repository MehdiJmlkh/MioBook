package ir.ac.ut.ece.ie.carts;

import ir.ac.ut.ece.ie.users.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends CrudRepository<CartItem, Long> {
    @Query("select c from CartItem c where c.cart.user.username = :username")
    List<CartItem> findCartItems(@Param("username") String username);

    @Query("select ci from CartItem ci where ci.id = :id and ci.cart.user = :user")
    Optional<CartItem> findByIdAndUser(@Param("id") Long id, @Param("user") User user);
}
