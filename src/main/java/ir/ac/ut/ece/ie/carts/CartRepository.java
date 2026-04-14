package ir.ac.ut.ece.ie.carts;

import ir.ac.ut.ece.ie.users.User;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class CartRepository {
    private final Set<Cart> carts = new LinkedHashSet<>();

    public void addItemToCart(User user, CartItem item) {
        var cart = getCart(user);
        cart.addItem(item);
    }

    public Optional<Cart> findByUser(User user) {
        return carts.stream()
                .filter(cart -> cart.getUser() == user)
                .findFirst();
    }

    public void removeCart(Cart cart) {
        carts.remove(cart);
    }

    private Cart getCart(User user) {
        var cart =  carts.stream()
                .filter(c -> c.getUser() == user)
                .findFirst().orElse(null);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            carts.add(cart);
        }
        return cart;
    }
}