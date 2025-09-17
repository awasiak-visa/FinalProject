package pl.coderslab.cafe;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.boardgame.BoardGame;
import pl.coderslab.boardgame.BoardGameService;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cafes")
public class CafeController {

    private final CafeService cafeService;
    private final BoardGameService boardGameService;
    private final Validator validator;

    public CafeController(CafeService cafeService, BoardGameService boardGameService, Validator validator) {
        this.cafeService = cafeService;
        this.boardGameService = boardGameService;
        this.validator = validator;
    }

    private CafeDTO convertCafeToDTO(Cafe cafe) {
        return CafeDTO.builder().id(cafe.getId()).name(cafe.getName())
                .openingTime(cafe.getOpeningTime()).closingTime(cafe.getClosingTime())
                .address(cafe.getAddress()).boardGames(cafe.getBoardGames())
                .build();
    }


    @PostMapping("")
    public void postCafe(@RequestBody Cafe cafe) {
        Set<ConstraintViolation<Cafe>> constraintViolations = validator.validate(cafe);
        if (constraintViolations.isEmpty()) {
            cafeService.createCafe(cafe);
        } else {
            throw new ConstraintViolationException(constraintViolations);
        }
    }

    @GetMapping("/{id}")
    public CafeDTO getCafe(@PathVariable("id") Long id) {
        if (cafeService.readCafeById(id).isPresent()) {
            return convertCafeToDTO(cafeService.readCafeById(id).get());
        } else {
            throw new RuntimeException("No board game found.");
        }
    }

    @PutMapping("")
    public void putCafe(@RequestBody Cafe cafe) {
        Set<ConstraintViolation<Cafe>> constraintViolations = validator.validate(cafe);
        if (constraintViolations.isEmpty()) {
            cafeService.updateCafe(cafe);
        } else {
            throw new ConstraintViolationException(constraintViolations);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteCafe(@PathVariable("id") Long id) {
        cafeService.deleteCafeById(id);
    }

    @GetMapping("")
    public List<CafeDTO> getAllCafes() {
        if (!cafeService.readAllCafes().isEmpty()) {
            return cafeService.readAllCafes().stream().map(this::convertCafeToDTO).collect(Collectors.toList());
        } else {
            throw new RuntimeException("No cafe found.");
        }
    }

    // find
    @GetMapping("/find-name/{name}")
    public List<CafeDTO> getCafesByName(@PathVariable("name") String name) {
        if (cafeService.findCafesByName(name).isPresent()) {
            return cafeService.findCafesByName(name).get().stream().map(this::convertCafeToDTO)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("No cafe found.");
        }
    }

    @GetMapping("/find-boardGameId/{boardGameId}")
    public List<CafeDTO> getCafesByBoardGameId(@PathVariable("boardGameId") Long boardGameId) {
        if (cafeService.findCafesByBoardGameId(boardGameId).isPresent()) {
            return cafeService.findCafesByBoardGameId(boardGameId).get().stream().map(this::convertCafeToDTO)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("No cafe found.");
        }
    }

    @GetMapping("/find-boardGameTitle-publisherName/{boardGameTitle}/{publisherName}")
    public List<CafeDTO> getCafesByBoardGameTitleAndPublisherName(
            @PathVariable("boardGameTitle") String boardGameTitle,
            @PathVariable("publisherName") String publisherName) {
        if (cafeService.findCafesByBoardGameTitleAndPublisherName(boardGameTitle, publisherName).isPresent()) {
            return cafeService.findCafesByBoardGameTitleAndPublisherName(boardGameTitle, publisherName)
                    .get().stream().map(this::convertCafeToDTO).collect(Collectors.toList());
        } else {
            throw new RuntimeException("No cafe found.");
        }
    }

    @GetMapping("/find-time/{time}")
    public List<CafeDTO> getCafesByTime(@PathVariable("time") LocalTime time) {
        if (cafeService.findCafesByTimeBetweenOpeningAndClosingTime(time).isPresent()) {
            return cafeService.findCafesByTimeBetweenOpeningAndClosingTime(time).get().stream().map(this::convertCafeToDTO)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("No cafe found.");
        }
    }

    @GetMapping("/find-address/{address}")
    public List<CafeDTO> getCafesByAddressContaining(@PathVariable("address") String address) {
        if (cafeService.findCafesByAddressContaining(address).isPresent()) {
            return cafeService.findCafesByAddressContaining(address).get().stream().map(this::convertCafeToDTO)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("No cafe found.");
        }
    }

    // update
    @PutMapping("/update/{id}/openingTime")
    public void putCafeOpeningTime(@RequestBody LocalTime openingTime, @PathVariable("id") Long id) {
        if (cafeService.readCafeById(id).isEmpty()) {
            throw new RuntimeException("No cafe found.");
        }
        cafeService.updateCafeOpeningTime(openingTime, id);
    }

    @PutMapping("/update/{id}/closingTime")
    public void putCafeClosingTime(@RequestBody LocalTime closingTime, @PathVariable("id") Long id) {
        if (cafeService.readCafeById(id).isEmpty()) {
            throw new RuntimeException("No cafe found.");
        }
        cafeService.updateCafeClosingTime(closingTime, id);
    }

    @PutMapping("/update/{id}/addBoardGame")
    public void putCafeBoardGamesAdd(@RequestBody Long boardGameId, @PathVariable("id") Long id) {
        if (cafeService.readCafeById(id).isEmpty()) {
            throw new RuntimeException("No cafe found.");
        }
        Optional<BoardGame> boardGame = boardGameService.readBoardGameById(boardGameId);
        boardGame.ifPresent(game -> cafeService.updateCafeBoardGamesAdd(game, id));
    }

    @PutMapping("/update/{id}/removeBoardGame")
    public void putCafeBoardGamesRemove(@RequestBody Long boardGameId, @PathVariable("id") Long id) {
        if (cafeService.readCafeById(id).isEmpty()) {
            throw new RuntimeException("No cafe found.");
        }
        Optional<BoardGame> boardGame = boardGameService.readBoardGameById(boardGameId);
        boardGame.ifPresent(game -> cafeService.updateCafeBoardGamesRemove(game, id));
    }
}