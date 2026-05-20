package ir.ac.ut.ece.ie.testdata;

import ir.ac.ut.ece.ie.authors.Author;
import ir.ac.ut.ece.ie.books.Book;
import ir.ac.ut.ece.ie.books.Genre;
import ir.ac.ut.ece.ie.purchases.Purchase;
import ir.ac.ut.ece.ie.purchases.PurchaseItem;
import ir.ac.ut.ece.ie.users.Admin;
import ir.ac.ut.ece.ie.users.Customer;
import ir.ac.ut.ece.ie.users.User;

import java.time.LocalDateTime;
import java.util.Set;

public class TestDataFactory {
    public static Admin sampleAdminUser() {
        var user = new Admin();
        user.setUsername("username");
        return user;
    }

    public static Customer sampleCustomerUser() {
        var user = new Customer();
        user.setUsername("username");
        return user;
    }

    public static Purchase samplePurchaseWithOneBorrowItem(User user) {
        var author = new Author();
        author.setName("name");

        var book = new Book();
        book.setTitle("title");
        book.setAuthor(author);
        book.setPrice(15);

        var purchaseItem = PurchaseItem.builder()
                .book(book)
                .isBorrowed(true)
                .borrowDays(5)
                .price(7)
                .date(LocalDateTime.now())
                .build();

        var purchase = new Purchase();
        purchase.setTotalCost(7);
        purchase.setUser(user);
        purchase.setDate(LocalDateTime.now());
        purchase.getItems().add(purchaseItem);

        return purchase;
    }

    public static Book sampleBook() {
        var author = new Author();
        author.setName("author's name");

        return Book.builder()
                .title("title")
                .author(author)
                .publisher("publisher")
                .year(2000)
                .genres(Set.of(new Genre("genre")))
                .price(15)
                .synopsis("synopsis")
                .content("content")
                .reviews(Set.of())
                .build();
    }

    public static Author sampleAuthor() {
        var author = new Author();
        author.setId(1L);
        author.setName("author's name");
        return author;
    }
}
