package ir.ac.ut.ece.ie.carts;

import ir.ac.ut.ece.ie.books.Book;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItem {
    private CartItemType type;
    private Book book;
    private Integer price;

    public static CartItem BuyCartItem(Book book) {
        var cartItem = new CartItem();
        cartItem.type = CartItemType.BUY;
        cartItem.book = book;
        cartItem.price = book.getPrice();
        return cartItem;
    }
}
