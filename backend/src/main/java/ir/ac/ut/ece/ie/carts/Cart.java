package ir.ac.ut.ece.ie.carts;

import ir.ac.ut.ece.ie.books.Book;
import ir.ac.ut.ece.ie.users.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @Column(name = "customer_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "customer_id")
    @MapsId
    private User user;


    private List<CartItem> items = new ArrayList<>();

    public boolean contains(Book book) {
        return items.stream().
                map(CartItem::getBook)
                .toList()
                .contains(book);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int getTotalPrice() {
        return items.stream()
                .map(CartItem::getPrice)
                .reduce(Integer::sum)
                .orElse(0);
    }

    public void addItem(CartItem item) {
        items.add(item);
    }

    public void removeBook(Book book) {
        items.stream()
                .filter(item -> item.getBook() == book)
                .findFirst()
                .ifPresent(cartItem -> items.remove(cartItem));
    }

    public List<Book> getBooks() {
        return items.stream()
                .map(CartItem::getBook)
                .toList();
    }
}
