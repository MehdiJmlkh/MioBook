package ir.ac.ut.ece.ie.carts;

import lombok.Data;

import java.util.Set;

@Data
public class CartItemDto {
    private Long id;
    private Long bookId;
    private Long authorId;
    private String title;
    private String author;
    private String publisher;
    private Set<String> genres;
    private Integer year;
    private String imageLink;
    private Boolean isBorrowed;
    private Integer borrowDays;
    private Integer price;
    private Integer finalPrice;

    public static CartItemDto fromCartItem(CartItem cartItem) {
        var cartItemDto = new CartItemDto();
        var book = cartItem.getBook();

        cartItemDto.id = cartItem.getId();
        cartItemDto.bookId = book.getId();
        cartItemDto.authorId = book.getAuthor().getId();
        cartItemDto.title = book.getTitle();
        cartItemDto.author = book.getAuthor().getName();
        cartItemDto.publisher = book.getPublisher();
        cartItemDto.genres = book.getGenreNames();
        cartItemDto.year = book.getYear();
        cartItemDto.imageLink = book.getImageLink();
        cartItemDto.price = book.getPrice();
        cartItemDto.isBorrowed = cartItem.getIsBorrowed();
        cartItemDto.borrowDays = cartItem.getBorrowDays();
        cartItemDto.finalPrice = cartItem.getPrice();

        return cartItemDto;
    }
}
