package pl.coderslab.cafe;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.boardgame.BoardGame;
import pl.coderslab.boardgame.BoardGameService;
import java.time.LocalTime;
import java.util.List;
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
        return convertCafeToDTO(cafeService.readCafeById(id));
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
        return cafeService.readAllCafes().stream().map(this::convertCafeToDTO).collect(Collectors.toList());
    }

    // find
    @GetMapping("/find-name/{name}")
    public List<CafeDTO> getCafesByName(@PathVariable("name") String name) {
        return cafeService.findCafesByName(name).stream().map(this::convertCafeToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/find-boardGameId/{boardGameId}")
    public List<CafeDTO> getCafesByBoardGameId(@PathVariable("boardGameId") Long boardGameId) {
        return cafeService.findCafesByBoardGameId(boardGameId).stream().map(this::convertCafeToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/find-boardGameTitle-publisherName/{boardGameTitle}/{publisherName}")
    public List<CafeDTO> getCafesByBoardGameTitleAndPublisherName(
            @PathVariable("boardGameTitle") String boardGameTitle,
            @PathVariable("publisherName") String publisherName) {
        return cafeService.findCafesByBoardGameTitleAndPublisherName(boardGameTitle, publisherName)
                .stream().map(this::convertCafeToDTO).collect(Collectors.toList());
    }

    @GetMapping("/find-time/{time}")
    public List<CafeDTO> getCafesByTime(@PathVariable("time") LocalTime time) {
        return cafeService.findCafesByTimeBetweenOpeningAndClosingTime(time).stream().map(this::convertCafeToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/find-address/{address}")
    public List<CafeDTO> getCafesByAddressContaining(@PathVariable("address") String address) {
        return cafeService.findCafesByAddressContaining(address).stream().map(this::convertCafeToDTO)
                .collect(Collectors.toList());
    }

    // update
    @PutMapping("/update/{id}/openingTime")
    public void putCafeOpeningTime(@RequestBody LocalTime openingTime, @PathVariable("id") Long id) {
        Cafe cafe = cafeService.readCafeById(id);
        cafe.setOpeningTime(openingTime);
        Set<ConstraintViolation<Cafe>> constraintViolations = validator.validate(cafe);
        if (constraintViolations.isEmpty()) {
            cafeService.updateCafeOpeningTime(openingTime, id);
        } else {
            throw new ConstraintViolationException(constraintViolations);
        }
    }

    @PutMapping("/update/{id}/closingTime")
    public void putCafeClosingTime(@RequestBody LocalTime closingTime, @PathVariable("id") Long id) {
        Cafe cafe = cafeService.readCafeById(id);
        cafe.setClosingTime(closingTime);
        Set<ConstraintViolation<Cafe>> constraintViolations = validator.validate(cafe);
        if (constraintViolations.isEmpty()) {
            cafeService.updateCafeClosingTime(closingTime, id);
        } else {
            throw new ConstraintViolationException(constraintViolations);
        }
    }

    @PutMapping("/update/{id}/addBoardGame")
    public void putCafeBoardGamesAdd(@RequestBody Long boardGameId, @PathVariable("id") Long id) {
        BoardGame boardGame = boardGameService.readBoardGameById(boardGameId);
        cafeService.updateCafeBoardGamesAdd(boardGame, id);
    }

    @PutMapping("/update/{id}/removeBoardGame")
    public void putCafeBoardGamesRemove(@RequestBody Long boardGameId, @PathVariable("id") Long id) {
        BoardGame boardGame = boardGameService.readBoardGameById(boardGameId);
        cafeService.updateCafeBoardGamesRemove(boardGame, id);
    }
}