package pl.coderslab.boardgame;

import org.springframework.stereotype.Service;
import pl.coderslab.Difficulty;
import pl.coderslab.boardgame.category.Category;
import pl.coderslab.boardgame.category.CategoryRepository;
import pl.coderslab.boardgame.publisher.Publisher;
import pl.coderslab.boardgame.publisher.PublisherRepository;
import pl.coderslab.review.ReviewRepository;
import java.util.List;

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

    public Category readCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found."));
    }

    public void updateCategory(Category category) {
        if (categoryRepository.findById(category.getId()).isEmpty()) {
            throw new RuntimeException("Category not found.");
        }
        categoryRepository.update(category.getName(), category.getId());
    }

    public void deleteCategoryById(Long id) {
        if (categoryRepository.findById(id).isEmpty()) {
            throw new RuntimeException("Category not found.");
        }
        categoryRepository.deleteById(id);
    }

    public List<Category> readAllCategories() {
        if (categoryRepository.findAll().isEmpty()) {
            throw new RuntimeException("Categories not found.");
        }
        return categoryRepository.findAll();
    }

    // Publisher
    public void createPublisher(Publisher publisher) {
        publisherRepository.save(publisher);
    }

    public Publisher readPublisherById(Long id) {
        return publisherRepository.findById(id).orElseThrow(() -> new RuntimeException("Publisher not found."));
    }

    public void updatePublisher(Publisher publisher) {
        if (publisherRepository.findById(publisher.getId()).isEmpty()) {
            throw new RuntimeException("Publisher not found.");
        }
        publisherRepository.update(publisher.getName(), publisher.getId());
    }

    public void deletePublisherById(Long id) {
        if (publisherRepository.findById(id).isEmpty()) {
            throw new RuntimeException("Publisher not found.");
        }
        publisherRepository.deleteById(id);
    }

    public List<Publisher> readAllPublishers() {
        if (publisherRepository.findAll().isEmpty()) {
            throw new RuntimeException("Publishers not found.");
        }
        return publisherRepository.findAll();
    }


    // BoardGame
    public void createBoardGame(BoardGame boardGame) {
        boardGame.setRating(0.0);
        if (boardGame.getMinPlayerCount() > boardGame.getMaxPlayerCount()) {
            throw new IllegalArgumentException("Min player count cannot be greater than max player count");
        } else if (boardGame.getMinTime() > boardGame.getMaxTime()) {
            throw new IllegalArgumentException("Min time cannot be greater than max time");
        }
        boardGameRepository.save(boardGame);
    }

    public BoardGame readBoardGameById(Long id) {
        return boardGameRepository.findById(id).orElseThrow(() -> new RuntimeException("Board game not found."));
    }

    public void updateBoardGame(BoardGame boardGame) {
        BoardGame originalBoardGame = boardGameRepository.findById(boardGame.getId())
                .orElseThrow(() -> new RuntimeException("Board game not found."));
        if (boardGame.getMinPlayerCount() > boardGame.getMaxPlayerCount()) {
            throw new IllegalArgumentException("Min player count cannot be greater than max player count");
        } else if (boardGame.getMinTime() > boardGame.getMaxTime()) {
            throw new IllegalArgumentException("Min time cannot be greater than max time");
        }
        originalBoardGame.setTitle(boardGame.getTitle());
        originalBoardGame.setPublisher(boardGame.getPublisher());
        originalBoardGame.setDescription(boardGame.getDescription());
        originalBoardGame.setMinPlayerCount(boardGame.getMinPlayerCount());
        originalBoardGame.setMaxPlayerCount(boardGame.getMaxPlayerCount());
        originalBoardGame.setMinTime(boardGame.getMinTime());
        originalBoardGame.setMaxTime(boardGame.getMaxTime());
        originalBoardGame.setDifficulty(boardGame.getDifficulty());
        originalBoardGame.setCategories(boardGame.getCategories());
        originalBoardGame.setRating(reviewRepository.findAverageRatingByBoardGameId(boardGame.getId()));
        boardGameRepository.save(originalBoardGame);

    }

    public void deleteBoardGameById(Long id) {
        if (boardGameRepository.findById(id).isEmpty()) {
            throw new RuntimeException("Board game not found.");
        }
        boardGameRepository.deleteById(id);
    }

    public List<BoardGame> readAllBoardGames() {
        if (boardGameRepository.findAll().isEmpty()) {
            throw new RuntimeException("Board games not found.");
        }
        return boardGameRepository.findAll();
    }

    // finding methods
    public List<BoardGame> findBoardGamesByTitle(String title) {
        if (boardGameRepository.findByTitle(title).isEmpty()) {
            throw new RuntimeException("Board games not found.");
        }
        return boardGameRepository.findByTitle(title);
    }

    public List<BoardGame> findBoardGamesByPublisherName(String publisherName) {
        if (boardGameRepository.findByPublisherName(publisherName).isEmpty()) {
            throw new RuntimeException("Board games not found.");
        }
        return boardGameRepository.findByPublisherName(publisherName);
    }

    public List<BoardGame> findBoardGamesByDifficulty(Difficulty difficulty) {
        if (boardGameRepository.findByDifficulty(difficulty).isEmpty()) {
            throw new RuntimeException("Board games not found.");
        }
        return boardGameRepository.findByDifficulty(difficulty);
    }

    public List<BoardGame> findBoardGamesByRatingGreaterThanEqual(Double rating) {
        if (boardGameRepository.findByRatingGreaterThanEqual(rating).isEmpty()) {
            throw new RuntimeException("Board games not found.");
        }
        return boardGameRepository.findByRatingGreaterThanEqual(rating);
    }

    public List<BoardGame> findBoardGamesByPlayerCountBetweenMinAndMaxPlayerCount(Integer playerCount) {
        if (boardGameRepository.findByPlayerCountBetweenMinAndMaxPlayerCount(playerCount).isEmpty()) {
            throw new RuntimeException("Board games not found.");
        }
        return boardGameRepository.findByPlayerCountBetweenMinAndMaxPlayerCount(playerCount);
    }

    public List<BoardGame> findBoardGamesByMaxTimeLessThanEqual(Integer maxTime) {
        if (boardGameRepository.findByMaxTimeLessThanEqual(maxTime).isEmpty()) {
            throw new RuntimeException("Board games not found.");
        }
        return boardGameRepository.findByMaxTimeLessThanEqual(maxTime);
    }

    public List<BoardGame> findBoardGamesByTimeBetweenMinAndMaxTime(Integer time) {
        if (boardGameRepository.findByTimeBetweenMinAndMaxTime(time).isEmpty()) {
            throw new RuntimeException("Board games not found.");
        }
        return boardGameRepository.findByTimeBetweenMinAndMaxTime(time);
    }

    public List<BoardGame> findBoardGamesByCategoryName(String categoryName) {
        if (boardGameRepository.findByCategoryName(categoryName).isEmpty()) {
            throw new RuntimeException("Board games not found.");
        }
        return boardGameRepository.findByCategoryName(categoryName);
    }

    // updating methods
    public void updateBoardGameDescription(String description, Long id) {
        if (boardGameRepository.findById(id).isEmpty()) {
            throw new RuntimeException("Board game not found.");
        }
        boardGameRepository.updateDescription(description, id);
    }

    public void updateBoardGameCategoriesAdd(Category category, Long id) {
        BoardGame boardGame = boardGameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board game not found."));
        if (!boardGame.getCategories().contains(category)) {
            boardGame.getCategories().add(category);
            boardGameRepository.save(boardGame);
        } else {
            throw new RuntimeException("Category already added to the board game.");
        }
    }

    public void updateBoardGameCategoriesRemove(Category category, Long id) {
        BoardGame boardGame = boardGameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board game not found."));
        if (boardGame.getCategories().contains(category)) {
            boardGame.getCategories().remove(category);
            boardGameRepository.save(boardGame);
        } else {
            throw new RuntimeException("Category is not added to the board game.");
        }
    }
}
