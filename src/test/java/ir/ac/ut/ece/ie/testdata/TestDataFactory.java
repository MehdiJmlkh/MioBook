package ir.ac.ut.ece.ie.testdata;

import ir.ac.ut.ece.ie.books.Book;
import ir.ac.ut.ece.ie.users.Role;
import ir.ac.ut.ece.ie.users.User;

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
}
