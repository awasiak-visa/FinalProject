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
        return convertReviewToDTO(reviewService.readReviewById(id));
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
        return reviewService.readAllReviews().stream().map(this::convertReviewToDTO).collect(Collectors.toList());
    }

    // find
    @GetMapping("/find-boardGameId/{boardGameId}")
    public List<ReviewDTO> getReviewsByBoardGameId(@PathVariable("boardGameId") Long boardGameId) {
        return reviewService.findReviewsByBoardGameId(boardGameId).stream().map(this::convertReviewToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/find-boardGameTitle-publisherName/{boardGameTitle}/{publisherName}")
    public List<ReviewDTO> getReviewsByBoardGameTitleAndPublisherName(
            @PathVariable("boardGameTitle") String boardGameTitle,
            @PathVariable("publisherName") String publisherName) {
        return reviewService.findReviewsByBoardGameTitleAndPublisherName(boardGameTitle, publisherName)
                .stream().map(this::convertReviewToDTO).collect(Collectors.toList());
    }

    @GetMapping("/find-userId/{userId}")
    public List<ReviewDTO> getReviewsByUserId(@PathVariable("userId") Long userId) {
        return reviewService.findReviewsByUserId(userId).stream().map(this::convertReviewToDTO)
                .collect(Collectors.toList());
    }

    // update
    @PutMapping("/update/{id}/rating")
    public void putReviewRating(@RequestBody Integer rating, @PathVariable("id") Long id) {
        Review review = reviewService.readReviewById(id);
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
        Review review = reviewService.readReviewById(id);
        review.setTitle(title);
        Set<ConstraintViolation<Review>> constraintViolations = validator.validate(review);
        if (constraintViolations.isEmpty()) {
            reviewService.updateReviewTitle(title, id);
        } else {
            throw new ConstraintViolationException(constraintViolations);
        }
    }

    @PutMapping("/update/{id}/description")
    public void putReviewDescription(@RequestBody String description, @PathVariable("id") Long id) {
        Review review = reviewService.readReviewById(id);
        review.setDescription(description);
        Set<ConstraintViolation<Review>> constraintViolations = validator.validate(review);
        if (constraintViolations.isEmpty()) {
            reviewService.updateReviewDescription(description, id);
        } else {
            throw new ConstraintViolationException(constraintViolations);
        }
    }
}
