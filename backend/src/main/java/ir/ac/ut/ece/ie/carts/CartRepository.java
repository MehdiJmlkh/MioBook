package ir.ac.ut.ece.ie.carts;

import ir.ac.ut.ece.ie.users.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CartRepository extends CrudRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}