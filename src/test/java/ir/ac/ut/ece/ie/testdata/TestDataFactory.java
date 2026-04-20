package ir.ac.ut.ece.ie.testdata;

import ir.ac.ut.ece.ie.authors.Author;
import ir.ac.ut.ece.ie.books.Book;
import ir.ac.ut.ece.ie.purchases.Purchase;
import ir.ac.ut.ece.ie.purchases.PurchaseItem;
import ir.ac.ut.ece.ie.users.Role;
import ir.ac.ut.ece.ie.users.User;

import java.time.LocalDateTime;

public class TestDataFactory {
    public static User sampleAdminUser() {
        var user = new User();
        user.setRole(Role.ADMIN);
        user.setUsername("username");
        return user;
    }

    public static User sampleCustomerUser() {
        var user = new User();
        user.setRole(Role.CUSTOMER);
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
}
