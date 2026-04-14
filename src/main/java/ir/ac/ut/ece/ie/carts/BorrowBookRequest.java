package ir.ac.ut.ece.ie.carts;

import lombok.Data;

@Data
public class BorrowBookRequest {
    private String username;
    private String title;
    private Integer days;
}
