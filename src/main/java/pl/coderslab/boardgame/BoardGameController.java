package pl.coderslab.boardgame;

import org.springframework.web.bind.annotation.*;
import pl.coderslab.Difficulty;
import pl.coderslab.boardgame.category.Category;
import pl.coderslab.boardgame.category.CategoryDTO;
import pl.coderslab.boardgame.publisher.Publisher;
import pl.coderslab.boardgame.publisher.PublisherDTO;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/boardgames")
public class BoardGameController {

    private final BoardGameService boardGameService;

    public BoardGameController(BoardGameService boardGameService) {
        this.boardGameService = boardGameService;
    }


    // Category
    private CategoryDTO convertCategoryToDTO(Category category) {
        return CategoryDTO.builder().id(category.getId()).name(category.getName()).build();
    }

    @PostMapping("/categories")
    public void postCategory(@RequestBody Category category) {
        boardGameService.createCategory(category);
    }

    @GetMapping("/categories/{id}")
    public CategoryDTO getCategory(@PathVariable("id") Long id) {
        if (boardGameService.readCategoryById(id).isPresent()) {
            return convertCategoryToDTO(boardGameService.readCategoryById(id).get());
        } else {
            throw new RuntimeException("No category found.");
        }
    }

    @PutMapping("/categories")
    public void putCategory(@RequestBody Category category) {
        boardGameService.updateCategory(category);
    }

    @DeleteMapping("/categories/{id}")
    public void deleteCategory(@PathVariable("id") Long id) {
        boardGameService.deleteCategoryById(id);
    }

    @GetMapping("/categories")
    public List<CategoryDTO> getAllCategories() {
        if (!boardGameService.readAllCategories().isEmpty()) {
            return boardGameService.readAllCategories().stream().map(this::convertCategoryToDTO)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("No category found.");
        }
    }


    // Publisher
    private PublisherDTO convertPublisherToDTO(Publisher publisher) {
        return PublisherDTO.builder().id(publisher.getId()).name(publisher.getName()).build();
    }

    @PostMapping("/publishers")
    public void postPublisher(@RequestBody Publisher publisher) {
        boardGameService.createPublisher(publisher);
    }

    @GetMapping("/publishers/{id}")
    public PublisherDTO getPublisher(@PathVariable("id") Long id) {
        if (boardGameService.readPublisherById(id).isPresent()) {
            return convertPublisherToDTO(boardGameService.readPublisherById(id).get());
        } else {
            throw new RuntimeException("No publisher found.");
        }
    }

    @PutMapping("/publishers")
    public void putPublisher(@RequestBody Publisher publisher) {
        boardGameService.updatePublisher(publisher);
    }

    @DeleteMapping("/publishers/{id}")
    public void deletePublisher(@PathVariable("id") Long id) {
        boardGameService.deletePublisherById(id);
    }

    @GetMapping("/publishers")
    public List<PublisherDTO> getAllPublishers() {
        if (!boardGameService.readAllPublishers().isEmpty()) {
            return boardGameService.readAllPublishers().stream().map(this::convertPublisherToDTO)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("No publisher found.");
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
    public void postBoardGame(@RequestBody BoardGame boardGame) {
        boardGameService.createBoardGame(boardGame);
    }

    @GetMapping("/{id}")
    public BoardGameDTO getBoardGame(@PathVariable("id") Long id) {
        if (boardGameService.readBoardGameById(id).isPresent()) {
            return convertBoardGameToDTO(boardGameService.readBoardGameById(id).get());
        } else {
            throw new RuntimeException("No board game found.");
        }
    }

    @PutMapping("")
    public void putBoardGame(@RequestBody BoardGame boardGame) {
        boardGameService.updateBoardGame(boardGame);
    }

    @DeleteMapping("/{id}")
    public void deleteBoardGame(@PathVariable("id") Long id) {
        boardGameService.deleteBoardGameById(id);
    }

    @GetMapping("")
    public List<BoardGameDTO> getAllBoardGames() {
        if (!boardGameService.readAllBoardGames().isEmpty()) {
            return boardGameService.readAllBoardGames().stream().map(this::convertBoardGameToDTO)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("No board game found.");
        }
    }

    // find
    @GetMapping("/find-title/{title}")
    public List<BoardGameDTO> getBoardGamesByTitle(@PathVariable("title") String title) {
        if (boardGameService.findBoardGamesByTitle(title).isPresent()) {
            return boardGameService.findBoardGamesByTitle(title).get().stream().map(this::convertBoardGameToDTO)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("No board game found.");
        }
    }

    @GetMapping("/find-publisherName/{publisherName}")
    public List<BoardGameDTO> getBoardGamesByPublisherName(@PathVariable("publisherName") String publisherName) {
        if (boardGameService.findBoardGamesByPublisherName(publisherName).isPresent()) {
            return boardGameService.findBoardGamesByPublisherName(publisherName).get().stream()
                    .map(this::convertBoardGameToDTO).collect(Collectors.toList());
        } else {
            throw new RuntimeException("No board game found.");
        }
    }

    @GetMapping("/find-difficulty/{difficulty}")
    public List<BoardGameDTO> getBoardGamesByDifficulty(@PathVariable("difficulty") Difficulty difficulty) {
        if (boardGameService.findBoardGamesByDifficulty(difficulty).isPresent()) {
            return boardGameService.findBoardGamesByDifficulty(difficulty).get().stream()
                    .map(this::convertBoardGameToDTO).collect(Collectors.toList());
        } else {
            throw new RuntimeException("No board game found.");
        }
    }

    @GetMapping("/find-rating/{rating}")
    public List<BoardGameDTO> getBoardGamesByRatingGreaterEqual(@PathVariable("rating") Double rating) {
        if (boardGameService.findBoardGamesByRatingGreaterThanEqual(rating).isPresent()) {
            return boardGameService.findBoardGamesByRatingGreaterThanEqual(rating).get().stream()
                    .map(this::convertBoardGameToDTO).collect(Collectors.toList());
        } else {
            throw new RuntimeException("No board game found.");
        }
    }

    @GetMapping("/find-playerCount/{playerCount}")
    public List<BoardGameDTO> getBoardGamesByPlayerCount(@PathVariable("playerCount") Integer playerCount) {
        if (boardGameService.findBoardGamesByPlayerCountBetweenMinAndMaxPlayerCount(playerCount).isPresent()) {
            return boardGameService.findBoardGamesByPlayerCountBetweenMinAndMaxPlayerCount(playerCount).get().stream()
                    .map(this::convertBoardGameToDTO).collect(Collectors.toList());
        } else {
            throw new RuntimeException("No board game found.");
        }
    }

    @GetMapping("/find-maxTime/{maxTime}")
    public List<BoardGameDTO> getBoardGamesByMaxTimeLessEqual(@PathVariable("maxTime") Integer maxTime) {
        if (boardGameService.findBoardGamesByMaxTimeLessThanEqual(maxTime).isPresent()) {
            return boardGameService.findBoardGamesByMaxTimeLessThanEqual(maxTime).get().stream()
                    .map(this::convertBoardGameToDTO).collect(Collectors.toList());
        } else {
            throw new RuntimeException("No board game found.");
        }
    }

    @GetMapping("/find-time/{time}")
    public List<BoardGameDTO> getBoardGamesByTime(@PathVariable("time") Integer time) {
        if (boardGameService.findBoardGamesByTimeBetweenMinAndMaxTime(time).isPresent()) {
            return boardGameService.findBoardGamesByTimeBetweenMinAndMaxTime(time).get().stream()
                    .map(this::convertBoardGameToDTO).collect(Collectors.toList());
        } else {
            throw new RuntimeException("No board game found.");
        }
    }

    @GetMapping("/find-categoryName/{categoryName}")
    public List<BoardGameDTO> getBoardGamesByCategoryName(@PathVariable("categoryName") String categoryName) {
        if (boardGameService.findBoardGamesByCategoryName(categoryName).isPresent()) {
            return boardGameService.findBoardGamesByCategoryName(categoryName).get().stream()
                    .map(this::convertBoardGameToDTO).collect(Collectors.toList());
        } else {
            throw new RuntimeException("No board game found.");
        }
    }

    // update
    @PutMapping("/update-description")
    public void putBoardGameDescription(@RequestBody Map<String, Object> params) {
        boardGameService.updateBoardGameDescription((String) params.get("description"), (Long) params.get("id"));
    }

    @PutMapping("/update-addCategory")
    public void putBoardGameCategoriesAdd(@RequestBody Map<String, Object> params) {
        Optional<Category> category = boardGameService.readCategoryById((Long) params.get("categoryId"));
        category.ifPresent(boardGameCategory -> boardGameService
                .updateBoardGameCategoriesAdd(boardGameCategory, (Long) params.get("id")));
    }

    @PutMapping("/update-removeCategory")
    public void putBoardGameCategoriesRemove(@RequestBody Map<String, Object> params) {
        Optional<Category> category = boardGameService.readCategoryById((Long) params.get("categoryId"));
        category.ifPresent(boardGameCategory -> boardGameService
                .updateBoardGameCategoriesRemove(boardGameCategory, (Long) params.get("id")));
    }
}
