package ir.ac.ut.ece.ie.reviews;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;

@Data
public class AddReviewRequest {
    private String username;
    private String title;

    @Range(min = 1, max = 5, message = "Rate must be between 1 and 5.")
    private Integer rate;
    private String comment;
}
