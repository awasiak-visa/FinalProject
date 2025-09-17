package pl.coderslab.boardgame;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.Difficulty;
import pl.coderslab.boardgame.category.Category;
import pl.coderslab.boardgame.category.CategoryDTO;
import pl.coderslab.boardgame.publisher.Publisher;
import pl.coderslab.boardgame.publisher.PublisherDTO;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/boardgames")
public class BoardGameController {

    private final BoardGameService boardGameService;
    private final Validator validator;

    public BoardGameController(BoardGameService boardGameService, Validator validator) {
        this.boardGameService = boardGameService;
        this.validator = validator;
    }


    // Category
    private CategoryDTO convertCategoryToDTO(Category category) {
        return CategoryDTO.builder().id(category.getId()).name(category.getName()).build();
    }

    @PostMapping("/categories")
    public void postCategory(@RequestBody Category category) {
        Set<ConstraintViolation<Category>> constraintViolations = validator.validate(category);
        if (constraintViolations.isEmpty()) {
            boardGameService.createCategory(category);
        } else {
            throw new ConstraintViolationException(constraintViolations);
        }
    }

    @GetMapping("/categories/{id}")
    public CategoryDTO getCategory(@PathVariable("id") Long id) {
        return convertCategoryToDTO(boardGameService.readCategoryById(id));
    }

    @PutMapping("/categories")
    public void putCategory(@RequestBody Category category) {
        Set<ConstraintViolation<Category>> constraintViolations = validator.validate(category);
        if (constraintViolations.isEmpty()) {
            boardGameService.updateCategory(category);
        } else {
            throw new ConstraintViolationException(constraintViolations);
        }
    }

    @DeleteMapping("/categories/{id}")
    public void deleteCategory(@PathVariable("id") Long id) {
        boardGameService.deleteCategoryById(id);
    }

    @GetMapping("/categories")
    public List<CategoryDTO> getAllCategories() {
        return boardGameService.readAllCategories().stream().map(this::convertCategoryToDTO)
                .collect(Collectors.toList());
    }


    // Publisher
    private PublisherDTO convertPublisherToDTO(Publisher publisher) {
        return PublisherDTO.builder().id(publisher.getId()).name(publisher.getName()).build();
    }

    @PostMapping("/publishers")
    public void postPublisher(@RequestBody Publisher publisher) {
        Set<ConstraintViolation<Publisher>> constraintViolations = validator.validate(publisher);
        if (constraintViolations.isEmpty()) {
            boardGameService.createPublisher(publisher);
        } else {
            throw new ConstraintViolationException(constraintViolations);
        }
    }

    @GetMapping("/publishers/{id}")
    public PublisherDTO getPublisher(@PathVariable("id") Long id) {
        return convertPublisherToDTO(boardGameService.readPublisherById(id));
    }

    @PutMapping("/publishers")
    public void putPublisher(@RequestBody Publisher publisher) {
        Set<ConstraintViolation<Publisher>> constraintViolations = validator.validate(publisher);
        if (constraintViolations.isEmpty()) {
            boardGameService.updatePublisher(publisher);
        } else {
            throw new ConstraintViolationException(constraintViolations);
        }
    }

    @DeleteMapping("/publishers/{id}")
    public void deletePublisher(@PathVariable("id") Long id) {
        boardGameService.deletePublisherById(id);
    }

    @GetMapping("/publishers")
    public List<PublisherDTO> getAllPublishers() {
        return boardGameService.readAllPublishers().stream().map(this::convertPublisherToDTO)
                .collect(Collectors.toList());
    }


    // BoardGame
    private BoardGameDTO convertBoardGameToDTO(BoardGame boardGame) {
        return BoardGameDTO.builder().id(boardGame.getId()).title(boardGame.getTitle())
                .publisherName(boardGame.getPublisher().getName()).description(boardGame.getDescription())
                .minPlayerCount(boardGame.getMinPlayerCount()).maxPlayerCount(boardGame.getMaxPlayerCount())
                .minTime(boardGame.getMinTime()).maxTime(boardGame.getMaxTime()).difficulty(boardGame.getDifficulty())
                .categoriesNames(boardGame.getCategories().stream().map(Category::getName).collect(Collectors.toList()))
                .rating(boardGame.getRating()).build();
    }

    @PostMapping("")
    public void postBoardGame(@RequestBody BoardGame boardGame) {
        Set<ConstraintViolation<BoardGame>> constraintViolations = validator.validate(boardGame);
        if (constraintViolations.isEmpty()) {
            boardGameService.createBoardGame(boardGame);
        } else {
            throw new ConstraintViolationException(constraintViolations);
        }
    }

    @GetMapping("/{id}")
    public BoardGameDTO getBoardGame(@PathVariable("id") Long id) {
        return convertBoardGameToDTO(boardGameService.readBoardGameById(id));
    }

    @PutMapping("")
    public void putBoardGame(@RequestBody BoardGame boardGame) {
        Set<ConstraintViolation<BoardGame>> constraintViolations = validator.validate(boardGame);
        if (constraintViolations.isEmpty()) {
            boardGameService.updateBoardGame(boardGame);
        } else {
            throw new ConstraintViolationException(constraintViolations);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteBoardGame(@PathVariable("id") Long id) {
        boardGameService.deleteBoardGameById(id);
    }

    @GetMapping("")
    public List<BoardGameDTO> getAllBoardGames() {
        return boardGameService.readAllBoardGames().stream().map(this::convertBoardGameToDTO)
                .collect(Collectors.toList());
    }

    // find
    @GetMapping("/find-title/{title}")
    public List<BoardGameDTO> getBoardGamesByTitle(@PathVariable("title") String title) {
        return boardGameService.findBoardGamesByTitle(title).stream().map(this::convertBoardGameToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/find-publisherName/{publisherName}")
    public List<BoardGameDTO> getBoardGamesByPublisherName(@PathVariable("publisherName") String publisherName) {
        return boardGameService.findBoardGamesByPublisherName(publisherName).stream()
                .map(this::convertBoardGameToDTO).collect(Collectors.toList());
    }

    @GetMapping("/find-difficulty/{difficulty}")
    public List<BoardGameDTO> getBoardGamesByDifficulty(@PathVariable("difficulty") Difficulty difficulty) {
        return boardGameService.findBoardGamesByDifficulty(difficulty).stream()
                .map(this::convertBoardGameToDTO).collect(Collectors.toList());
    }

    @GetMapping("/find-rating/{rating}")
    public List<BoardGameDTO> getBoardGamesByRatingGreaterEqual(@PathVariable("rating") Double rating) {
        return boardGameService.findBoardGamesByRatingGreaterThanEqual(rating).stream()
                .map(this::convertBoardGameToDTO).collect(Collectors.toList());
    }

    @GetMapping("/find-playerCount/{playerCount}")
    public List<BoardGameDTO> getBoardGamesByPlayerCount(@PathVariable("playerCount") Integer playerCount) {
        return boardGameService.findBoardGamesByPlayerCountBetweenMinAndMaxPlayerCount(playerCount).stream()
                .map(this::convertBoardGameToDTO).collect(Collectors.toList());
    }

    @GetMapping("/find-maxTime/{maxTime}")
    public List<BoardGameDTO> getBoardGamesByMaxTimeLessEqual(@PathVariable("maxTime") Integer maxTime) {
        return boardGameService.findBoardGamesByMaxTimeLessThanEqual(maxTime).stream()
                .map(this::convertBoardGameToDTO).collect(Collectors.toList());
    }

    @GetMapping("/find-time/{time}")
    public List<BoardGameDTO> getBoardGamesByTime(@PathVariable("time") Integer time) {
        return boardGameService.findBoardGamesByTimeBetweenMinAndMaxTime(time).stream()
                .map(this::convertBoardGameToDTO).collect(Collectors.toList());
    }

    @GetMapping("/find-categoryName/{categoryName}")
    public List<BoardGameDTO> getBoardGamesByCategoryName(@PathVariable("categoryName") String categoryName) {
        return boardGameService.findBoardGamesByCategoryName(categoryName).stream()
                .map(this::convertBoardGameToDTO).collect(Collectors.toList());
    }

    // update
    @PutMapping("/update/{id}/description")
    public void putBoardGameDescription(@RequestBody String description, @PathVariable("id") Long id) {
        boardGameService.updateBoardGameDescription(description, id);
    }

    @PutMapping("/update/{id}/addCategory")
    public void putBoardGameCategoriesAdd(@RequestBody Long categoryId, @PathVariable("id") Long id) {
        Category category = boardGameService.readCategoryById(categoryId);
        boardGameService.updateBoardGameCategoriesAdd(category, id);
    }

    @PutMapping("/update/{id}/removeCategory")
    public void putBoardGameCategoriesRemove(@RequestBody Long categoryId, @PathVariable("id") Long id) {
        Category category = boardGameService.readCategoryById(categoryId);
        boardGameService.updateBoardGameCategoriesRemove(category, id);
    }
}
