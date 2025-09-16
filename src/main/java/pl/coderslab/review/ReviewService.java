package pl.coderslab.review;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public void createReview(Review review) {
        reviewRepository.save(review);
    }
    public Optional<Review> readReviewById(Long id) {
        return reviewRepository.findById(id);
    }
    public void updateReview(Review review) {
        reviewRepository.update(review.getBoardGame(), review.getTitle(), review.getDescription(), review.getId());
    }
    public void deleteReviewById(Long id) {
        reviewRepository.deleteById(id);
    }
    public List<Review> readAllReviews() {
        return reviewRepository.findAll();
    }

    public Optional<List<Review>> findReviewsByBoardGameId(Long boardGameId) {
        return reviewRepository.findByBoardGameId(boardGameId);
    }
    public Optional<List<Review>> findReviewsByBoardGameTitleAndPublisherName(String boardGameTitle, String publisherName) {
        return reviewRepository.findByBoardGameTitleAndPublisherName(boardGameTitle, publisherName);
    }

    public Optional<List<Review>> findReviewsByUserId(Long userId) {
        return reviewRepository.findByUserId(userId);
    }
    public Optional<List<Review>> findReviewsByUserUsername(String username) {
        return reviewRepository.findByUserUsername(username);
    }
}
