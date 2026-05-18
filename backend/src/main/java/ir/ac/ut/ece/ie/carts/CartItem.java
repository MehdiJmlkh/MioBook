package ir.ac.ut.ece.ie.carts;

import ir.ac.ut.ece.ie.books.Book;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "is_borrowed")
    private Boolean isBorrowed;

    @Column(name = "borrow_days")
    private Integer borrowDays;

    @Column(name = "price")
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;


    public static CartItem BuyCartItem(Book book) {
        var cartItem = new CartItem();
        cartItem.isBorrowed = false;
        cartItem.book = book;
        cartItem.price = book.getPrice();
        return cartItem;
    }

    public static CartItem BorrowCartItem(Book book, Integer days) {
        var cartItem = new CartItem();
        cartItem.isBorrowed = true;
        cartItem.book = book;
        cartItem.borrowDays = days;
        cartItem.price = (book.getPrice() * days) / 10;
        return cartItem;
    }
}
