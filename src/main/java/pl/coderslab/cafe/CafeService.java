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

    public Optional<List<Cafe>> findCafeByName(String name) {
        return cafeRepository.findByName(name);
    }
    
    public Optional<List<Cafe>> findCafeByBoardGameId(Long boardGameId) {
        return cafeRepository.findByBoardGameId(boardGameId);
    }
    public Optional<List<Cafe>> findCafeByBoardGamTitleAndPublisherName(String boardGameTitle, String publisherName) {
        return cafeRepository.findByBoardGameTitleAndPublisherName(boardGameTitle, publisherName);
    }

    public Optional<List<Cafe>> findCafeByOpeningTimeBefore(LocalTime openingTime) {
        return cafeRepository.findByOpeningTimeIsBefore(openingTime);
    }
    public Optional<List<Cafe>> findCafeByClosingTimeAfter(LocalTime closingTime) {
        return cafeRepository.findByClosingTimeIsAfter(closingTime);
    }
    
    public Optional<List<Cafe>> findCafeByAddressContaining(String address) {
        return cafeRepository.findByAddressContainingIgnoreCase(address);
    }
}
