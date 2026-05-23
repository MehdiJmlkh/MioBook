package ir.ac.ut.ece.ie.credits;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class AddCreditRequest {
    private String username;

    @Range(min = 100, message = "Credit must be at least $1.")
    private Integer credit;
}
