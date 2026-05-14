package ir.ac.ut.ece.ie.books;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookPageDto {
    List<BookDto> books;
    int totalBooks;
}
