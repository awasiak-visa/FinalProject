package pl.coderslab.cafe;

import org.springframework.stereotype.Service;
import pl.coderslab.boardgame.BoardGame;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class CafeService {
    private CafeRepository cafeRepository;

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

    public Optional<List<Cafe>> findCafesByName(String name) {
        return cafeRepository.findByName(name);
    }
    
    public Optional<List<Cafe>> findCafesByBoardGameId(Long boardGameId) {
        return cafeRepository.findByBoardGameId(boardGameId);
    }
    public Optional<List<Cafe>> findCafesByBoardGamTitleAndPublisherName(String boardGameTitle, String publisherName) {
        return cafeRepository.findByBoardGameTitleAndPublisherName(boardGameTitle, publisherName);
    }

    public Optional<List<Cafe>> findCafesByOpeningTimeBefore(LocalTime openingTime) {
        return cafeRepository.findByOpeningTimeIsBefore(openingTime);
    }
    public Optional<List<Cafe>> findCafesByClosingTimeAfter(LocalTime closingTime) {
        return cafeRepository.findByClosingTimeIsAfter(closingTime);
    }
    
    public Optional<List<Cafe>> findCafesByAddressContaining(String address) {
        return cafeRepository.findByAddressContainingIgnoreCase(address);
    }

    public void updateCafeAddress(String address, Long id) {
        cafeRepository.updateAddress(address, id);
    }

    public void updateCafeName(String name, Long id) {
        cafeRepository.updateName(name, id);
    }

    public void updateCafeOpenHours(LocalTime openingTime, LocalTime closingTime, Long id) {
        cafeRepository.updateOpeningTimeAndClosingTime(openingTime, closingTime, id);
    }

    public void addBoardGameToCafe(Long boardGameId, Long id) {
        cafeRepository.updateBoardGamesAddBoardGameById(boardGameId, id);
    }
    public void removeBoardGameFromCafe(Long boardGameId, Long id) {
        cafeRepository.updateBoardGamesRemoveBoardGameById(boardGameId, id);
    }
}
