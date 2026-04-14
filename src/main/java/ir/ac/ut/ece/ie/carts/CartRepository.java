package ir.ac.ut.ece.ie.carts;

import org.springframework.stereotype.Repository;

import java.util.LinkedHashSet;
import java.util.Set;

@Repository
public class CartRepository {
    private final Set<Cart> carts = new LinkedHashSet<>();
}
