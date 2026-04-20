package ir.ac.ut.ece.ie.reviews;

import ir.ac.ut.ece.ie.books.BookNotInStockException;
import ir.ac.ut.ece.ie.common.ErrorDto;
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

    @ExceptionHandler(BookNotInStockException.class)
    public ResponseEntity<ErrorDto> handleBookNotInStockException() {
        return ResponseEntity.badRequest()
                .body(new ErrorDto("You don’t have this book in stock right now."));
    }
}
