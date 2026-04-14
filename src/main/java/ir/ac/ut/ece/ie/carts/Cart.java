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
import java.util.function.BinaryOperator;

@Getter
@Setter
public class Cart {
    private User user;
    private List<Book> books = new ArrayList<>();

    public boolean contains(Book book) {
        return books.contains(book);
    }

    public boolean isEmpty() {
        return books.isEmpty();
    }

    public int getTotalPrice() {
        return books.stream()
                .map(Book::getPrice)
                .reduce(Integer::sum)
                .orElse(0);
    }
}
