package ir.ac.ut.ece.ie.carts;

import ir.ac.ut.ece.ie.books.Book;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItem {
    private Boolean isBorrowed;
    private Integer borrowDays;
    private Book book;
    private Integer price;

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
        cartItem.price = book.getPrice() * (days / 10);
        return cartItem;
    }
}
