package ir.ac.ut.ece.ie.carts;

import ir.ac.ut.ece.ie.books.Book;
import ir.ac.ut.ece.ie.users.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.control.MappingControl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class Cart {
    private User user;
    private List<Book> books = new ArrayList<>();

    public boolean contains(Book book) {
        return books.contains(book);
    }
}
