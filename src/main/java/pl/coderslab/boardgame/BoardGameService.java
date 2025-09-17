package pl.coderslab.boardgame;

import org.springframework.stereotype.Service;
import pl.coderslab.Difficulty;
import pl.coderslab.boardgame.category.Category;
import pl.coderslab.boardgame.category.CategoryRepository;
import pl.coderslab.boardgame.publisher.Publisher;
import pl.coderslab.boardgame.publisher.PublisherRepository;
import pl.coderslab.review.ReviewRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BoardGameService {

    private final BoardGameRepository boardGameRepository;
    private final CategoryRepository categoryRepository;
    private final PublisherRepository publisherRepository;
    private final ReviewRepository reviewRepository;

    public BoardGameService(BoardGameRepository boardGameRepository, CategoryRepository categoryRepository,
                            PublisherRepository publisherRepository, ReviewRepository reviewRepository) {
        this.boardGameRepository = boardGameRepository;
        this.categoryRepository = categoryRepository;
        this.publisherRepository = publisherRepository;
        this.reviewRepository = reviewRepository;
    }


    // Category
    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    public Optional<Category> readCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public void updateCategory(Category category) {
        categoryRepository.update(category.getName(), category.getId());
    }

    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }

    public List<Category> readAllCategories() {
        return categoryRepository.findAll();
    }

    // Publisher
    public void createPublisher(Publisher publisher) {
        publisherRepository.save(publisher);
    }

    public Optional<Publisher> readPublisherById(Long id) {
        return publisherRepository.findById(id);
    }

    public void updatePublisher(Publisher publisher) {
        publisherRepository.update(publisher.getName(), publisher.getId());
    }

    public void deletePublisherById(Long id) {
        publisherRepository.deleteById(id);
    }

    public List<Publisher> readAllPublishers() {
        return publisherRepository.findAll();
    }


    // BoardGame
    public void createBoardGame(BoardGame boardGame) {
        boardGame.setRating(0.0);
        boardGameRepository.save(boardGame);
    }

    public Optional<BoardGame> readBoardGameById(Long id) {
        return boardGameRepository.findById(id);
    }

    public void updateBoardGame(BoardGame boardGame) {
        boardGameRepository.update(boardGame.getTitle(), boardGame.getPublisher(), boardGame.getDescription(),
                boardGame.getMinPlayerCount(), boardGame.getMaxPlayerCount(), boardGame.getMinTime(),
                boardGame.getMaxTime(), boardGame.getDifficulty(), boardGame.getCategories(),
                reviewRepository.findAverageRatingByBoardGameId(boardGame.getId()), boardGame.getId());
    }

    public void deleteBoardGameById(Long id) {
        boardGameRepository.deleteById(id);
    }

    public List<BoardGame> readAllBoardGames() {
        return boardGameRepository.findAll();
    }

    // finding methods
    public Optional<List<BoardGame>> findBoardGamesByTitle(String title) {
        return boardGameRepository.findByTitle(title);
    }

    public Optional<List<BoardGame>> findBoardGamesByPublisherName(String publisherName) {
        return boardGameRepository.findByPublisherName(publisherName);
    }

    public Optional<List<BoardGame>> findBoardGamesByDifficulty(Difficulty difficulty) {
        return boardGameRepository.findByDifficulty(difficulty);
    }

    public Optional<List<BoardGame>> findBoardGamesByRatingGreaterThanEqual(Double rating) {
        return boardGameRepository.findByRatingGreaterThanEqual(rating);
    }

    public Optional<List<BoardGame>> findBoardGamesByPlayerCountBetweenMinAndMaxPlayerCount(Integer playerCount) {
        return boardGameRepository.findByPlayerCountBetweenMinAndMaxPlayerCount(playerCount);
    }

    public Optional<List<BoardGame>> findBoardGamesByMaxTimeLessThanEqual(Integer maxTime) {
        return boardGameRepository.findByMaxTimeLessThanEqual(maxTime);
    }

    public Optional<List<BoardGame>> findBoardGamesByTimeBetweenMinAndMaxTime(Integer time) {
        return boardGameRepository.findByTimeBetweenMinAndMaxTime(time);
    }

    public Optional<List<BoardGame>> findBoardGamesByCategoryName(String categoryName) {
        return boardGameRepository.findByCategoryName(categoryName);
    }

    // updating methods
    public void updateBoardGameDescription(String description, Long id) {
        boardGameRepository.updateDescription(description, id);
    }

    public void updateBoardGameCategoriesAdd(Category category, Long id) {
        if (boardGameRepository.findById(id).isPresent()) {
            List<Category> categories = boardGameRepository.findById(id).get().getCategories();
            categories.add(category);
            boardGameRepository.updateCategories(categories, id);
        }
    }

    public void updateBoardGameCategoriesRemove(Category category, Long id) {
        if (boardGameRepository.findById(id).isPresent()) {
            List<Category> categories = boardGameRepository.findById(id).get().getCategories();
            categories.remove(category);
            boardGameRepository.updateCategories(categories, id);
        }
    }
}
