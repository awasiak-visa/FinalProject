package pl.coderslab.review;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final Validator validator;

    public ReviewController(ReviewService reviewService, Validator validator) {
        this.reviewService = reviewService;
        this.validator = validator;
    }

    private ReviewDTO convertReviewToDTO(Review review) {
        return ReviewDTO.builder().id(review.getId()).boardGameTitle(review.getBoardGame().getTitle())
                .rating(review.getRating()).description(review.getDescription())
                .username(review.getUser().getUsername()).build();
    }

    @PostMapping("")
    public void postReview(@RequestBody Review review) {
        Set<ConstraintViolation<Review>> constraintViolations = validator.validate(review);
        if (constraintViolations.isEmpty()) {
            reviewService.createReview(review);
        } else {
            throw new ConstraintViolationException(constraintViolations);
        }
    }

    @GetMapping("/{id}")
    public ReviewDTO getReview(@PathVariable("id") Long id) {
        if (reviewService.readReviewById(id).isPresent()) {
            return convertReviewToDTO(reviewService.readReviewById(id).get());
        } else {
            throw new RuntimeException("No review found.");
        }
    }

    @PutMapping("")
    public void putReview(@RequestBody Review review) {
        Set<ConstraintViolation<Review>> constraintViolations = validator.validate(review);
        if (constraintViolations.isEmpty()) {
            reviewService.updateReview(review);
        } else {
            throw new ConstraintViolationException(constraintViolations);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable("id") Long id) {
        reviewService.deleteReviewById(id);
    }

    @GetMapping("")
    public List<ReviewDTO> getAllReviews() {
        if (!reviewService.readAllReviews().isEmpty()) {
            return reviewService.readAllReviews().stream().map(this::convertReviewToDTO).collect(Collectors.toList());
        } else {
            throw new RuntimeException("No review found.");
        }
    }

    // find
    @GetMapping("/find-boardGameId/{boardGameId}")
    public List<ReviewDTO> getReviewsByBoardGameId(@PathVariable("boardGameId") Long boardGameId) {
        if (reviewService.findReviewsByBoardGameId(boardGameId).isPresent()) {
            return reviewService.findReviewsByBoardGameId(boardGameId).get().stream().map(this::convertReviewToDTO)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("No review found.");
        }
    }

    @GetMapping("/find-boardGameTitle-publisherName/{boardGameTitle}/{publisherName}")
    public List<ReviewDTO> getReviewsByBoardGameTitleAndPublisherName(
            @PathVariable("boardGameTitle") String boardGameTitle,
            @PathVariable("publisherName") String publisherName) {
        if (reviewService.findReviewsByBoardGameTitleAndPublisherName(boardGameTitle, publisherName).isPresent()) {
            return reviewService.findReviewsByBoardGameTitleAndPublisherName(boardGameTitle, publisherName)
                    .get().stream().map(this::convertReviewToDTO).collect(Collectors.toList());
        } else {
            throw new RuntimeException("No review found.");
        }
    }

    @GetMapping("/find-userId/{userId}")
    public List<ReviewDTO> getReviewsByUserId(@PathVariable("userId") Long userId) {
        if (reviewService.findReviewsByUserId(userId).isPresent()) {
            return reviewService.findReviewsByUserId(userId).get().stream().map(this::convertReviewToDTO)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("No review found.");
        }
    }

    // update
    @PutMapping("/update/{id}/rating")
    public void putReviewRating(@RequestBody Integer rating, @PathVariable("id") Long id) {
        if (reviewService.readReviewById(id).isEmpty()) {
            throw new RuntimeException("No review found.");
        }
        Review review = new Review();
        review.setRating(rating);
        Set<ConstraintViolation<Review>> constraintViolations = validator.validate(review);
        if (constraintViolations.isEmpty()) {
            reviewService.updateReviewRating(rating, id);
        } else {
            throw new ConstraintViolationException(constraintViolations);
        }
    }

    @PutMapping("/update/{id}/title")
    public void putReviewTitle(@RequestBody String title, @PathVariable("id") Long id) {
        if (reviewService.readReviewById(id).isEmpty()) {
            throw new RuntimeException("No review found.");
        }
        reviewService.updateReviewTitle(title, id);
    }

    @PutMapping("/update/{id}/description")
    public void putReviewDescription(@RequestBody String description, @PathVariable("id") Long id) {
        if (reviewService.readReviewById(id).isEmpty()) {
            throw new RuntimeException("No review found.");
        }
        reviewService.updateReviewDescription(description, id);
    }
}
