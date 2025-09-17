package pl.coderslab.review;

import org.springframework.stereotype.Service;
import pl.coderslab.boardgame.BoardGameRepository;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BoardGameRepository boardGameRepository;

    public ReviewService(ReviewRepository reviewRepository, BoardGameRepository boardGameRepository) {
        this.reviewRepository = reviewRepository;
        this.boardGameRepository = boardGameRepository;
    }

    public void createReview(Review review) {
        reviewRepository.save(review);
        Double averageRating = reviewRepository.findAverageRatingByBoardGameId(review.getBoardGame().getId());
        boardGameRepository.updateRating(averageRating, review.getBoardGame().getId());
    }

    public Review readReviewById(Long id) {
        return reviewRepository.findById(id).orElseThrow(() -> new RuntimeException("Review not found."));
    }

    public void updateReview(Review review) {
        if (reviewRepository.findById(review.getId()).isEmpty()) {
            throw new RuntimeException("Review not found.");
        }
        reviewRepository.update(review.getBoardGame(), review.getRating(), review.getTitle(), review.getDescription(),
                review.getUser(), review.getId());
        Double averageRating = reviewRepository.findAverageRatingByBoardGameId(review.getBoardGame().getId());
        boardGameRepository.updateRating(averageRating, review.getBoardGame().getId());
    }

    public void deleteReviewById(Long id) {
        if (reviewRepository.findById(id).isEmpty()) {
            throw new RuntimeException("Review not found.");
        }
        reviewRepository.deleteById(id);
    }

    public List<Review> readAllReviews() {
        if (reviewRepository.findAll().isEmpty()) {
            throw new RuntimeException("Reviews not found.");
        }
        return reviewRepository.findAll();
    }

    // finding methods
    public List<Review> findReviewsByBoardGameId(Long boardGameId) {
        if (reviewRepository.findByBoardGameId(boardGameId).isEmpty()) {
            throw new RuntimeException("Reviews not found.");
        }
        return reviewRepository.findByBoardGameId(boardGameId);
    }

    public List<Review> findReviewsByBoardGameTitleAndPublisherName(String boardGameTitle, String publisherName) {
        if (reviewRepository.findByBoardGameTitleAndPublisherName(boardGameTitle, publisherName).isEmpty()) {
            throw new RuntimeException("Reviews not found.");
        }
        return reviewRepository.findByBoardGameTitleAndPublisherName(boardGameTitle, publisherName);
    }

    public List<Review> findReviewsByUserId(Long userId) {
        if (reviewRepository.findByUserId(userId).isEmpty()) {
            throw new RuntimeException("Reviews not found.");
        }
        return reviewRepository.findByUserId(userId);
    }


    // updating methods
    public void updateReviewRating(Integer rating, Long id) {
        if (reviewRepository.findById(id).isEmpty()) {
            throw new RuntimeException("Review not found.");
        }
        reviewRepository.updateRating(rating, id);
        Double averageRating = reviewRepository.findAverageRatingByBoardGameId(reviewRepository.findById(id).get()
                .getBoardGame().getId());
        boardGameRepository.updateRating(averageRating, reviewRepository.findById(id).get().getBoardGame().getId());
    }

    public void updateReviewTitle(String title, Long id) {
        if (reviewRepository.findById(id).isEmpty()) {
            throw new RuntimeException("Review not found.");
        }
        reviewRepository.updateTitle(title, id);
    }

    public void updateReviewDescription(String description, Long id) {
        if (reviewRepository.findById(id).isEmpty()) {
            throw new RuntimeException("Review not found.");
        }
        reviewRepository.updateDescription(description, id);
    }
}
