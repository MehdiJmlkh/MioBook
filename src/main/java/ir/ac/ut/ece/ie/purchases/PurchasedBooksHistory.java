package ir.ac.ut.ece.ie.purchases;

import lombok.Data;

import java.util.List;

@Data
public class PurchasedBooksHistory {
    private String username;
    private List<PurchasedBookDto> books;
}
