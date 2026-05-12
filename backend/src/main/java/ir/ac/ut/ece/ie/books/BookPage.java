package ir.ac.ut.ece.ie.books;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BookPage {
    List<Book> books;
    int totalBooks;
}
