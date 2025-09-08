package pl.coderslab.boardgame;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BoardGameService {
    private final BoardGameRepository boardGameRepository;
    private final CategoryRepository categoryRepository;
    private final PublisherRepository publisherRepository;

    public BoardGameService(BoardGameRepository boardGameRepository,
                            CategoryRepository categoryRepository, PublisherRepository publisherRepository) {
        this.boardGameRepository = boardGameRepository;
        this.categoryRepository = categoryRepository;
        this.publisherRepository = publisherRepository;
    }


    // Category
    public void createCategory(Category category) {
        categoryRepository.save(category);
    }
    public Optional<Category> readCategoryById(Long id) {
        return categoryRepository.findById(id);
    }
    public Optional<Category> readCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }
    public void updateCategory(Category category) {
        categoryRepository.update(category.getName(), category.getId());
    }
    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }
    public void deleteCategoryByName(String name) {
        categoryRepository.deleteByName(name);
    }

    // Publisher
    public void createPublisher(Publisher publisher) {
        publisherRepository.save(publisher);
    }
    public Optional<Publisher> readPublisherById(Long id) {
        return publisherRepository.findById(id);
    }
    public Optional<Publisher> readPublisherByName(String name) {
        return publisherRepository.findByName(name);
    }
    public void updatePublisher(Publisher publisher) {
        publisherRepository.update(publisher.getName(), publisher.getId());
    }
    public void deletePublisherById(Long id) {
        publisherRepository.deleteById(id);
    }
    public void deletePublisherByName(String name) {
        publisherRepository.deleteByName(name);
    }

    // BoardGame
    public void createBoardGame(BoardGame boardGame) {
        boardGameRepository.save(boardGame);
    }
    public Optional<BoardGame> readBoardGameById(Long id) {
        return boardGameRepository.findById(id);
    }
    public void updateBoardGame(BoardGame boardGame) {
        boardGameRepository.update(boardGame.getTitle(), boardGame.getPublisher(), boardGame.getDescription(),
                boardGame.getPlayerCount(), boardGame.getTime(), boardGame.getDifficulty(), boardGame.getCategories(),
                boardGame.getRating());
    }
    public void deleteBoardGameById(Long id) {
        boardGameRepository.deleteById(id);
    }

}
