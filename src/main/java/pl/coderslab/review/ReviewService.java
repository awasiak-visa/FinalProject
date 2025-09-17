package pl.coderslab.review;

import org.springframework.stereotype.Service;
import pl.coderslab.boardgame.BoardGameRepository;
import java.util.List;
import java.util.Optional;

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

    public Optional<Review> readReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    public void updateReview(Review review) {
        reviewRepository.update(review.getBoardGame(), review.getRating(), review.getTitle(), review.getDescription(),
                review.getUser(), review.getId());
        Double averageRating = reviewRepository.findAverageRatingByBoardGameId(review.getBoardGame().getId());
        boardGameRepository.updateRating(averageRating, review.getBoardGame().getId());
    }

    public void deleteReviewById(Long id) {
        reviewRepository.deleteById(id);
    }

    public List<Review> readAllReviews() {
        return reviewRepository.findAll();
    }

    // finding methods
    public Optional<List<Review>> findReviewsByBoardGameId(Long boardGameId) {
        return reviewRepository.findByBoardGameId(boardGameId);
    }

    public Optional<List<Review>> findReviewsByBoardGameTitleAndPublisherName(String boardGameTitle, String publisherName) {
        return reviewRepository.findByBoardGameTitleAndPublisherName(boardGameTitle, publisherName);
    }

    public Optional<List<Review>> findReviewsByUserId(Long userId) {
        return reviewRepository.findByUserId(userId);
    }


    // updating methods
    public void updateReviewRating(Integer rating, Long id) {
        reviewRepository.updateRating(rating, id);
        if (reviewRepository.findById(id).isPresent()) {
            Double averageRating = reviewRepository.findAverageRatingByBoardGameId(reviewRepository.findById(id).get()
                    .getBoardGame().getId());
            boardGameRepository.updateRating(averageRating, reviewRepository.findById(id).get().getBoardGame().getId());
        }
    }

    public void updateReviewTitle(String title, Long id) {
        reviewRepository.updateTitle(title, id);
    }

    public void updateReviewDescription(String description, Long id) {
        reviewRepository.updateDescription(description, id);
    }
}
