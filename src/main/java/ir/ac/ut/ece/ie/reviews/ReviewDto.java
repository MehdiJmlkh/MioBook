package ir.ac.ut.ece.ie.reviews;

import lombok.Data;

@Data
public class ReviewDto {
    private String username;
    private String comment;
    private Integer rate;
}
