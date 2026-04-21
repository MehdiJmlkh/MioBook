package ir.ac.ut.ece.ie.reviews;

import ir.ac.ut.ece.ie.books.Book;
import ir.ac.ut.ece.ie.users.User;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Review {
    private Integer rate;
    private String comment;
    private LocalDate date;
    private Book book;
    private User user;
}
