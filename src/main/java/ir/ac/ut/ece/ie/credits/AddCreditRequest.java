package ir.ac.ut.ece.ie.credits;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class AddCreditRequest {
    private String username;
    @Min(value = 1, message = "Credit must be between 1 and 1000 cents.")
    @Max(value = 1000, message = "Credit must be between 1 and 1000 cents.")
    private Integer credit;
}
