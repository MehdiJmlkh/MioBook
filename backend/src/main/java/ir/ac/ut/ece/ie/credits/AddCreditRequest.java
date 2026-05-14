package ir.ac.ut.ece.ie.credits;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class AddCreditRequest {
    private Long id;

    @Range(min = 1, max = 1000, message = "Credit must be between 1 and 1000 cents.")
    private Integer credit;
}
