package pl.coderslab.review;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    private ReviewDTO convertReviewToDTO(Review review) {
        return ReviewDTO.builder().id(review.getId()).boardGameTitle(review.getBoardGame().getTitle())
                .rating(review.getRating()).description(review.getDescription())
                .username(review.getUser().getUsername()).build();
    }

    @PostMapping("")
    public void postReview(@RequestBody Review review) {
        reviewService.createReview(review);
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
        reviewService.updateReview(review);
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
    @PutMapping("/update-rating")
    public void putReviewRating(@RequestBody Map<String, Object> params) {
        reviewService.updateReviewRating((Double) params.get("rating"), (Long) params.get("id"));
    }

    @PutMapping("/update-title")
    public void putReviewTitle(@RequestBody Map<String, Object> params) {
        reviewService.updateReviewTitle((String) params.get("title"), (Long) params.get("id"));
    }

    @PutMapping("/update-description")
    public void putReviewDescription(@RequestBody Map<String, Object> params) {
        reviewService.updateReviewDescription((String) params.get("description"), (Long) params.get("id"));
    }
}
