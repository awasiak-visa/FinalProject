package pl.coderslab.boardgame;

import org.springframework.stereotype.Service;

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
}
