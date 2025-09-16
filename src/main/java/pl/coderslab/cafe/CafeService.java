package pl.coderslab.cafe;

import org.springframework.stereotype.Service;
import pl.coderslab.boardgame.BoardGame;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class CafeService {

    private final CafeRepository cafeRepository;

    public CafeService(CafeRepository cafeRepository) {
        this.cafeRepository = cafeRepository;
    }

    public void createCafe(Cafe cafe) {
        cafeRepository.save(cafe);
    }

    public Optional<Cafe> readCafeById(Long id) {
        return cafeRepository.findById(id);
    }

    public void updateCafe(Cafe cafe) {
        cafeRepository.update(cafe.getName(), cafe.getOpeningTime(), cafe.getClosingTime(), cafe.getAddress(),
                cafe.getBoardGames(), cafe.getId());
    }

    public void deleteCafeById(Long id) {
        cafeRepository.deleteById(id);
    }

    public List<Cafe> readAllCafes() {
        return cafeRepository.findAll();
    }

    // finding methods
    public Optional<List<Cafe>> findCafesByName(String name) {
        return cafeRepository.findByName(name);
    }

    public Optional<List<Cafe>> findCafesByBoardGameId(Long boardGameId) {
        return cafeRepository.findByBoardGameId(boardGameId);
    }

    public Optional<List<Cafe>> findCafesByTimeBetweenOpeningAndClosingTime(LocalTime time) {
        return cafeRepository.findByTimeBetweenOpeningAndClosingTime(time);
    }

    public Optional<List<Cafe>> findCafesByAddressContaining(String address) {
        return cafeRepository.findByAddressContainingIgnoreCase(address);
    }

    // updating methods
    public void updateCafeOpenHours(LocalTime openingTime, LocalTime closingTime, Long id) {
        cafeRepository.updateOpeningTimeAndClosingTime(openingTime, closingTime, id);
    }

    public void updateCafeBoardGamesAdd(BoardGame boardGame, Long id) {
        List<BoardGame> boardGames = cafeRepository.findById(id).get().getBoardGames();
        boardGames.add(boardGame);
        cafeRepository.updateBoardGames(boardGames, id);
    }

    public void updateCafeBoardGamesRemove(BoardGame boardGame, Long id) {
        List<BoardGame> boardGames = cafeRepository.findById(id).get().getBoardGames();
        boardGames.remove(boardGame);
        cafeRepository.updateBoardGames(boardGames, id);
    }
}
