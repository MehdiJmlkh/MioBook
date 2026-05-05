package ir.ac.ut.ece.ie.carts;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class CartItemDto {
    private String title;
    private String author;
    private String publisher;
    private Set<String> genres;
    private Integer year;
    private Boolean isBorrowed;
    private Integer borrowDays;
    private Integer price;
    private Integer finalPrice;

    public static CartItemDto fromCartItem(CartItem cartItem) {
        var cartItemDto = new CartItemDto();
        var book = cartItem.getBook();

        cartItemDto.title = book.getTitle();
        cartItemDto.author = book.getAuthor().getName();
        cartItemDto.publisher = book.getPublisher();
        cartItemDto.genres = book.getGenres();
        cartItemDto.year = book.getYear();
        cartItemDto.price = book.getPrice();
        cartItemDto.isBorrowed = cartItem.getIsBorrowed();
        cartItemDto.borrowDays = cartItem.getBorrowDays();
        cartItemDto.finalPrice = cartItem.getPrice();

        return cartItemDto;
    }
}
