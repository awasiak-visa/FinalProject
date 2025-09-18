package pl.coderslab.review;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.user.UserService;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static pl.coderslab.ValidationUtils.validationMessage;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final Validator validator;
    private final UserService userService;

    public ReviewController(ReviewService reviewService, Validator validator, UserService userService) {
        this.reviewService = reviewService;
        this.validator = validator;
        this.userService = userService;
    }

    private ReviewDTO convertReviewToDTO(Review review) {
        return ReviewDTO.builder().id(review.getId()).boardGameTitle(review.getBoardGame().getTitle())
                .rating(review.getRating()).description(review.getDescription())
                .username(review.getUser().getUsername()).build();
    }

    @PostMapping("")
    public ResponseEntity<String> postReview(@RequestBody Review review, HttpSession session) {
        if (session.getAttribute("userId") != null) {
            Set<ConstraintViolation<Review>> constraintViolations = validator.validate(review);
            if (constraintViolations.isEmpty()) {
                review.setUser(userService.readUserById((Long) session.getAttribute("userId")));
                reviewService.createReview(review);
                return ResponseEntity.ok("Review created");
            } else {
                return ResponseEntity.status(400).body(validationMessage(constraintViolations));
            }
        } else {
            return ResponseEntity.status(403).body("Forbidden");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getReview(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(convertReviewToDTO(reviewService.readReviewById(id)));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("")
    public ResponseEntity<String> putReview(@RequestBody Review review, HttpSession session) {
        if (session.getAttribute("userId").equals(review.getUser().getId())
                || session.getAttribute("role").equals("ROLE_ADMIN")) {
            Set<ConstraintViolation<Review>> constraintViolations = validator.validate(review);
            if (constraintViolations.isEmpty()) {
                reviewService.updateReview(review);
                return ResponseEntity.ok("Review updated");
            } else {
                return ResponseEntity.status(400).body(validationMessage(constraintViolations));
            }
        } else {
            return ResponseEntity.status(403).body("Forbidden");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable("id") Long id, HttpSession session) {
        if (session.getAttribute("userId").equals(reviewService.readReviewById(id).getUser().getId())
                || session.getAttribute("role").equals("ROLE_ADMIN")) {
            reviewService.deleteReviewById(id);
            return ResponseEntity.ok("Review deleted");
        } else {
            return ResponseEntity.status(403).body("Forbidden");
        }
    }

    @GetMapping("")
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        try {
            return ResponseEntity.ok(reviewService.readAllReviews().stream().map(this::convertReviewToDTO)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    // find
    @GetMapping("/find-boardGameId/{boardGameId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByBoardGameId(@PathVariable("boardGameId") Long boardGameId) {
        try {
            return ResponseEntity.ok(reviewService.findReviewsByBoardGameId(boardGameId).stream()
                    .map(this::convertReviewToDTO).collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/find-boardGameTitle-publisherName/{boardGameTitle}/{publisherName}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByBoardGameTitleAndPublisherName(
            @PathVariable("boardGameTitle") String boardGameTitle,
            @PathVariable("publisherName") String publisherName) {
        try {
            return ResponseEntity.ok(reviewService.findReviewsByBoardGameTitleAndPublisherName(boardGameTitle, publisherName)
                    .stream().map(this::convertReviewToDTO).collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/find-userId/{userId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByUserId(@PathVariable("userId") Long userId) {
        try {
            return ResponseEntity.ok(reviewService.findReviewsByUserId(userId).stream().map(this::convertReviewToDTO)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    // update
    @PutMapping("/update/{id}/rating")
    public ResponseEntity<String> putReviewRating(@RequestBody Integer rating, @PathVariable("id") Long id,
                                                  HttpSession session) {
        if (session.getAttribute("userId").equals(reviewService.readReviewById(id).getUser().getId())) {
            Review review = reviewService.readReviewById(id);
            review.setRating(rating);
            Set<ConstraintViolation<Review>> constraintViolations = validator.validate(review);
            if (constraintViolations.isEmpty()) {
                reviewService.updateReviewRating(rating, id);
                return ResponseEntity.ok("Review updated");
            } else {
                return ResponseEntity.status(400).body(validationMessage(constraintViolations));
            }
        } else {
            return ResponseEntity.status(403).body("Forbidden");
        }
    }

    @PutMapping("/update/{id}/title")
    public ResponseEntity<String> putReviewTitle(@RequestBody String title, @PathVariable("id") Long id,
                                                 HttpSession session) {
        if (session.getAttribute("userId").equals(reviewService.readReviewById(id).getUser().getId())
                || session.getAttribute("role").equals("ROLE_ADMIN")) {
            Review review = reviewService.readReviewById(id);
            review.setTitle(title);
            Set<ConstraintViolation<Review>> constraintViolations = validator.validate(review);
            if (constraintViolations.isEmpty()) {
                reviewService.updateReviewTitle(title, id);
                return ResponseEntity.ok("Review updated");
            } else {
                return ResponseEntity.status(400).body(validationMessage(constraintViolations));
            }
        } else {
            return ResponseEntity.status(403).body("Forbidden");
        }
    }

    @PutMapping("/update/{id}/description")
    public ResponseEntity<String> putReviewDescription(@RequestBody String description, @PathVariable("id") Long id,
                                                       HttpSession session) {
        if (session.getAttribute("userId").equals(reviewService.readReviewById(id).getUser().getId())
                || session.getAttribute("role").equals("ROLE_ADMIN")) {
            Review review = reviewService.readReviewById(id);
            review.setDescription(description);
            Set<ConstraintViolation<Review>> constraintViolations = validator.validate(review);
            if (constraintViolations.isEmpty()) {
                reviewService.updateReviewDescription(description, id);
                return ResponseEntity.ok("Review updated");
            } else {
                return ResponseEntity.status(400).body(validationMessage(constraintViolations));
            }
        } else {
            return ResponseEntity.status(403).body("Forbidden");
        }
    }
}
