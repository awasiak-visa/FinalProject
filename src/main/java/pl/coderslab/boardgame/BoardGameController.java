package pl.coderslab.boardgame;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.Difficulty;
import pl.coderslab.boardgame.category.Category;
import pl.coderslab.boardgame.category.CategoryDTO;
import pl.coderslab.boardgame.publisher.Publisher;
import pl.coderslab.boardgame.publisher.PublisherDTO;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static pl.coderslab.ValidationUtils.validationMessage;

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
    public ResponseEntity<String> postCategory(@RequestBody Category category, HttpSession session) {
        if (session.getAttribute("role").equals("ROLE_ADMIN")) {
            Set<ConstraintViolation<Category>> constraintViolations = validator.validate(category);
            if (constraintViolations.isEmpty()) {
                boardGameService.createCategory(category);
                return ResponseEntity.ok("Category created");
            } else {
                return ResponseEntity.status(400).body(validationMessage(constraintViolations));
            }
        } else {
            return ResponseEntity.status(403).body("Forbidden");
        }
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(convertCategoryToDTO(boardGameService.readCategoryById(id)));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("/categories")
    public ResponseEntity<String> putCategory(@RequestBody Category category, HttpSession session) {
        if (session.getAttribute("role").equals("ROLE_ADMIN")) {
            Set<ConstraintViolation<Category>> constraintViolations = validator.validate(category);
            if (constraintViolations.isEmpty()) {
                boardGameService.updateCategory(category);
                return ResponseEntity.ok("Category updated");
            } else {
                return ResponseEntity.status(400).body(validationMessage(constraintViolations));
            }
        } else {
            return ResponseEntity.status(403).body("Forbidden");
        }
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long id, HttpSession session) {
        if (session.getAttribute("role").equals("ROLE_ADMIN")) {
            boardGameService.deleteCategoryById(id);
            return ResponseEntity.ok("Category deleted");
        } else {
            return ResponseEntity.status(403).body("Forbidden");
        }
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        try {
            return ResponseEntity.ok(boardGameService.readAllCategories().stream().map(this::convertCategoryToDTO)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }


    // Publisher
    private PublisherDTO convertPublisherToDTO(Publisher publisher) {
        return PublisherDTO.builder().id(publisher.getId()).name(publisher.getName()).build();
    }

    @PostMapping("/publishers")
    public ResponseEntity<String> postPublisher(@RequestBody Publisher publisher, HttpSession session) {
        if (session.getAttribute("role").equals("ROLE_ADMIN")) {
            Set<ConstraintViolation<Publisher>> constraintViolations = validator.validate(publisher);
            if (constraintViolations.isEmpty()) {
                boardGameService.createPublisher(publisher);
                return ResponseEntity.ok("Publisher created");
            } else {
                return ResponseEntity.status(400).body(validationMessage(constraintViolations));
            }
        } else {
            return ResponseEntity.status(403).body("Forbidden");
        }
    }

    @GetMapping("/publishers/{id}")
    public ResponseEntity<PublisherDTO> getPublisher(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(convertPublisherToDTO(boardGameService.readPublisherById(id)));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("/publishers")
    public ResponseEntity<String> putPublisher(@RequestBody Publisher publisher, HttpSession session) {
        if (session.getAttribute("role").equals("ROLE_ADMIN")) {
            Set<ConstraintViolation<Publisher>> constraintViolations = validator.validate(publisher);
            if (constraintViolations.isEmpty()) {
                boardGameService.updatePublisher(publisher);
                return ResponseEntity.ok("Publisher updated");
            } else {
                return ResponseEntity.status(400).body(validationMessage(constraintViolations));
            }
        } else {
            return ResponseEntity.status(403).body("Forbidden");
        }
    }

    @DeleteMapping("/publishers/{id}")
    public ResponseEntity<String> deletePublisher(@PathVariable("id") Long id, HttpSession session) {
        if (session.getAttribute("role").equals("ROLE_ADMIN")) {
            boardGameService.deletePublisherById(id);
            return ResponseEntity.ok("Publisher deleted");
        } else {
            return ResponseEntity.status(403).body("Forbidden");
        }
    }

    @GetMapping("/publishers")
    public ResponseEntity<List<PublisherDTO>> getAllPublishers() {
        try {
            return ResponseEntity.ok(boardGameService.readAllPublishers().stream().map(this::convertPublisherToDTO)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
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
    public ResponseEntity<String> postBoardGame(@RequestBody BoardGame boardGame, HttpSession session) {
        if (session.getAttribute("role").equals("ROLE_ADMIN")) {
            Set<ConstraintViolation<BoardGame>> constraintViolations = validator.validate(boardGame);
            if (constraintViolations.isEmpty()) {
                boardGameService.createBoardGame(boardGame);
                return ResponseEntity.ok("Board game created");
            } else {
                return ResponseEntity.status(400).body(validationMessage(constraintViolations));
            }
        } else {
            return ResponseEntity.status(403).body("Forbidden");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardGameDTO> getBoardGame(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(convertBoardGameToDTO(boardGameService.readBoardGameById(id)));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("")
    public ResponseEntity<String> putBoardGame(@RequestBody BoardGame boardGame, HttpSession session) {
        if (session.getAttribute("role").equals("ROLE_ADMIN")) {
            Set<ConstraintViolation<BoardGame>> constraintViolations = validator.validate(boardGame);
            if (constraintViolations.isEmpty()) {
                boardGameService.updateBoardGame(boardGame);
                return ResponseEntity.ok("Board game updated");
            } else {
                return ResponseEntity.status(400).body(validationMessage(constraintViolations));
            }
        } else {
            return ResponseEntity.status(403).body("Forbidden");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBoardGame(@PathVariable("id") Long id, HttpSession session) {
        if (session.getAttribute("role").equals("ROLE_ADMIN")) {
            boardGameService.deleteBoardGameById(id);
            return ResponseEntity.ok("Board game deleted");
        } else {
            return ResponseEntity.status(403).body("Forbidden");
        }
    }

    @GetMapping("")
    public ResponseEntity<List<BoardGameDTO>> getAllBoardGames() {
        try {
            return ResponseEntity.ok(boardGameService.readAllBoardGames().stream().map(this::convertBoardGameToDTO)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    // find
    @GetMapping("/find-title/{title}")
    public ResponseEntity<List<BoardGameDTO>> getBoardGamesByTitle(@PathVariable("title") String title) {
        try {
            return ResponseEntity.ok(boardGameService.findBoardGamesByTitle(title).stream().map(this::convertBoardGameToDTO)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/find-publisherName/{publisherName}")
    public ResponseEntity<List<BoardGameDTO>> getBoardGamesByPublisherName(
            @PathVariable("publisherName") String publisherName) {
        try {
            return ResponseEntity.ok(boardGameService.findBoardGamesByPublisherName(publisherName).stream()
                    .map(this::convertBoardGameToDTO).collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/find-difficulty/{difficulty}")
    public ResponseEntity<List<BoardGameDTO>> getBoardGamesByDifficulty(
            @PathVariable("difficulty") Difficulty difficulty) {
        try {
            return ResponseEntity.ok(boardGameService.findBoardGamesByDifficulty(difficulty).stream()
                    .map(this::convertBoardGameToDTO).collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/find-rating/{rating}")
    public ResponseEntity<List<BoardGameDTO>> getBoardGamesByRatingGreaterEqual(@PathVariable("rating") Double rating) {
        try {
            return ResponseEntity.ok(boardGameService.findBoardGamesByRatingGreaterThanEqual(rating).stream()
                    .map(this::convertBoardGameToDTO).collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/find-playerCount/{playerCount}")
    public ResponseEntity<List<BoardGameDTO>> getBoardGamesByPlayerCount(
            @PathVariable("playerCount") Integer playerCount) {
        try {
            return ResponseEntity.ok(boardGameService.findBoardGamesByPlayerCountBetweenMinAndMaxPlayerCount(playerCount)
                    .stream().map(this::convertBoardGameToDTO).collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/find-maxTime/{maxTime}")
    public ResponseEntity<List<BoardGameDTO>> getBoardGamesByMaxTimeLessEqual(@PathVariable("maxTime") Integer maxTime) {
        try {
            return ResponseEntity.ok(boardGameService.findBoardGamesByMaxTimeLessThanEqual(maxTime).stream()
                    .map(this::convertBoardGameToDTO).collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/find-time/{time}")
    public ResponseEntity<List<BoardGameDTO>> getBoardGamesByTime(@PathVariable("time") Integer time) {
        try {
            return ResponseEntity.ok(boardGameService.findBoardGamesByTimeBetweenMinAndMaxTime(time).stream()
                    .map(this::convertBoardGameToDTO).collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/find-categoryName/{categoryName}")
    public ResponseEntity<List<BoardGameDTO>> getBoardGamesByCategoryName(
            @PathVariable("categoryName") String categoryName) {
        try {
            return ResponseEntity.ok(boardGameService.findBoardGamesByCategoryName(categoryName).stream()
                    .map(this::convertBoardGameToDTO).collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    // update
    @PutMapping("/update/{id}/description")
    public ResponseEntity<String> putBoardGameDescription(@RequestBody String description, @PathVariable("id") Long id,
                                                          HttpSession session) {
        if (session.getAttribute("role").equals("ROLE_ADMIN")) {
            boardGameService.updateBoardGameDescription(description, id);
            return ResponseEntity.ok("Board game updated");
        } else {
            return ResponseEntity.status(403).body("Forbidden");
        }
    }

    @PutMapping("/update/{id}/addCategory")
    public ResponseEntity<String> putBoardGameCategoriesAdd(@RequestBody Long categoryId, @PathVariable("id") Long id,
                                                            HttpSession session) {
        if (session.getAttribute("role").equals("ROLE_ADMIN")) {
            Category category = boardGameService.readCategoryById(categoryId);
            boardGameService.updateBoardGameCategoriesAdd(category, id);
            return ResponseEntity.ok("Board game updated");
        } else {
            return ResponseEntity.status(403).body("Forbidden");
        }
    }

    @PutMapping("/update/{id}/removeCategory")
    public ResponseEntity<String> putBoardGameCategoriesRemove(@RequestBody Long categoryId,
                                                               @PathVariable("id") Long id, HttpSession session) {
        if (session.getAttribute("role").equals("ROLE_ADMIN")) {
            Category category = boardGameService.readCategoryById(categoryId);
            boardGameService.updateBoardGameCategoriesRemove(category, id);
            return ResponseEntity.ok("Board game updated");
        } else {
            return ResponseEntity.status(403).body("Forbidden");
        }
    }
}
