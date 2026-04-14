package ir.ac.ut.ece.ie.carts;

import ir.ac.ut.ece.ie.books.Book;
import ir.ac.ut.ece.ie.users.User;
import org.mapstruct.control.MappingControl;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class CartRepository {
    private final Set<Cart> carts = new LinkedHashSet<>();

    public void addCart(User user, Book book) {
        var cart = getCart(user);
        cart.getBooks().add(book);
    }

    public Optional<Cart> findByUser(User user) {
        return carts.stream()
                .filter(cart -> cart.getUser() == user)
                .findFirst();
    }

    public void deleteCart(Cart cart) {
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