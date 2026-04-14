package ir.ac.ut.ece.ie.carts;

import ir.ac.ut.ece.ie.books.Book;
import ir.ac.ut.ece.ie.users.User;
import org.mapstruct.control.MappingControl;

import java.util.Set;

public class Cart {
    private User user;
    private Set<Book> books;
}
