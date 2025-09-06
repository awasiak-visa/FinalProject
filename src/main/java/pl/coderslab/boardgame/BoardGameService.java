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
    public Optional<Category> readCategory(Long id) {
        return categoryRepository.findById(id);
    }
    public void updateCategory(Category category) {
        categoryRepository.update(category.getName(), category.getId());
    }
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    // Publisher
    public void createPublisher(Publisher publisher) {
        publisherRepository.save(publisher);
    }
    public Optional<Publisher> readPublisher(Long id) {
        return publisherRepository.findById(id);
    }
    public void updatePublisher(Publisher publisher) {
        publisherRepository.update(publisher.getName(), publisher.getId());
    }
    public void deletePublisher(Long id) {
        publisherRepository.deleteById(id);
    }

    // BoardGame
    public void createBoardGame(BoardGame boardGame) {
        boardGameRepository.save(boardGame);
    }
    public Optional<BoardGame> readBoardGame(Long id) {
        return boardGameRepository.findById(id);
    }
    public void updateBoardGame(BoardGame boardGame) {
        boardGameRepository.update(boardGame.getTitle(), boardGame.getPublisher(), boardGame.getDescription(),
                boardGame.getPlayerCount(), boardGame.getTime(), boardGame.getDifficulty(), boardGame.getCategories(),
                boardGame.getRating());
    }
    public void deleteBoardGame(Long id) {
        boardGameRepository.deleteById(id);
    }

}
