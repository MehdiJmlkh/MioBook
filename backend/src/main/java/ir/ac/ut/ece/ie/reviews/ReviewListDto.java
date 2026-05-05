package ir.ac.ut.ece.ie.reviews;

import lombok.Data;

import java.util.List;

@Data
public class ReviewListDto {
    private String title;
    private List<ReviewDto> reviews;
    private float averageRating;
    private int totalReviews;
}
