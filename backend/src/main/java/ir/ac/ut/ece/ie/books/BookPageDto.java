package ir.ac.ut.ece.ie.books;

import lombok.Data;

import java.util.List;

@Data
public class BookPageDto {
    List<BookDto> books;
    int totalBooks;
}
