package pl.coderslab.review;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    private ReviewDTO convertReviewToDTO(Review review) {
        return ReviewDTO.builder().id(review.getId()).boardGameTitle(review.getBoardGame().getTitle())
                .score(review.getScore()).description(review.getDescription())
                .username(review.getUser().getUsername()).build();
    }
}
