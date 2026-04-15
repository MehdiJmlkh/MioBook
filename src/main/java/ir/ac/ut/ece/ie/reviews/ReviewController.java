package ir.ac.ut.ece.ie.reviews;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/{title}")
    public ReviewListDto getAllReviews(@PathVariable("title") String title) {
        return reviewService.getAllReviews(title);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addReview(@Valid @RequestBody AddReviewRequest request) {
        reviewService.addReview(request);
        return ResponseEntity.ok().build();
    }
}
