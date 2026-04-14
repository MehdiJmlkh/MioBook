package ir.ac.ut.ece.ie.carts;

import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.util.Map;

@Data
public class BorrowBookRequest {
    private String username;
    private String title;

    @Range(min = 1, max = 9, message = "Days must be between 1 and 9.")
    private Integer days;
}
