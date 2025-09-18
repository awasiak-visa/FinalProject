package pl.coderslab.cafe;

import org.springframework.stereotype.Service;
import pl.coderslab.boardgame.BoardGame;
import java.time.LocalTime;
import java.util.List;

@Service
public class CafeService {

    private final CafeRepository cafeRepository;

    public CafeService(CafeRepository cafeRepository) {
        this.cafeRepository = cafeRepository;
    }

    public void createCafe(Cafe cafe) {
        cafeRepository.save(cafe);
    }

    public Cafe readCafeById(Long id) {
        return cafeRepository.findById(id).orElseThrow(() -> new RuntimeException("Cafe not found."));
    }

    public void updateCafe(Cafe cafe) {
        Cafe originalCafe = cafeRepository.findById(cafe.getId())
                .orElseThrow(() -> new RuntimeException("Cafe not found."));
        originalCafe.setName(cafe.getName());
        originalCafe.setOpeningTime(cafe.getOpeningTime());
        originalCafe.setClosingTime(cafe.getClosingTime());
        originalCafe.setAddress(cafe.getAddress());
        originalCafe.setBoardGames(cafe.getBoardGames());
        cafeRepository.save(originalCafe);
    }

    public void deleteCafeById(Long id) {
        if (cafeRepository.findById(id).isEmpty()) {
            throw new RuntimeException("Cafe not found.");
        }
        cafeRepository.deleteById(id);
    }

    public List<Cafe> readAllCafes() {
        if (cafeRepository.findAll().isEmpty()) {
            throw new RuntimeException("Cafes not found.");
        }
        return cafeRepository.findAll();
    }

    // finding methods
    public List<Cafe> findCafesByName(String name) {
        if (cafeRepository.findByName(name).isEmpty()) {
            throw new RuntimeException("Cafes not found.");
        }
        return cafeRepository.findByName(name);
    }

    public List<Cafe> findCafesByBoardGameId(Long boardGameId) {
        if (cafeRepository.findByBoardGameId(boardGameId).isEmpty()) {
            throw new RuntimeException("Cafes not found.");
        }
        return cafeRepository.findByBoardGameId(boardGameId);
    }

    public List<Cafe> findCafesByBoardGameTitleAndPublisherName(String boardGameTitle, String publisherName) {
        if (cafeRepository.findByBoardGameTitleAndPublisherName(boardGameTitle, publisherName).isEmpty()) {
            throw new RuntimeException("Cafes not found.");
        }
        return cafeRepository.findByBoardGameTitleAndPublisherName(boardGameTitle, publisherName);
    }

    public List<Cafe> findCafesByTimeBetweenOpeningAndClosingTime(LocalTime time) {
        if (cafeRepository.findByTimeBetweenOpeningAndClosingTime(time).isEmpty()) {
            throw new RuntimeException("Cafes not found.");
        }
        return cafeRepository.findByTimeBetweenOpeningAndClosingTime(time);
    }

    public List<Cafe> findCafesByAddressContaining(String address) {
        if (cafeRepository.findByAddressContainingIgnoreCase(address).isEmpty()) {
            throw new RuntimeException("Cafes not found.");
        }
        return cafeRepository.findByAddressContainingIgnoreCase(address);
    }

    // updating methods
    public void updateCafeOpeningTime(LocalTime openingTime, Long id) {
        if (cafeRepository.findById(id).isEmpty()) {
            throw new RuntimeException("Cafe not found.");
        }
        cafeRepository.updateOpeningTime(openingTime, id);
    }

    public void updateCafeClosingTime(LocalTime closingTime, Long id) {
        if (cafeRepository.findById(id).isEmpty()) {
            throw new RuntimeException("Cafe not found.");
        }
        cafeRepository.updateClosingTime(closingTime, id);
    }

    public void updateCafeBoardGamesAdd(BoardGame boardGame, Long id) {
        Cafe cafe = cafeRepository.findById(id).orElseThrow(() -> new RuntimeException("Cafe not found."));
        cafe.getBoardGames().add(boardGame);
        cafeRepository.save(cafe);
    }

    public void updateCafeBoardGamesRemove(BoardGame boardGame, Long id) {
        Cafe cafe = cafeRepository.findById(id).orElseThrow(() -> new RuntimeException("Cafe not found."));
        if (!cafe.getBoardGames().contains(boardGame)) {
            throw new RuntimeException("Board game is not in the cafe.");
        }
        cafe.getBoardGames().remove(boardGame);
        cafeRepository.save(cafe);
    }
}
