package pl.coderslab.cafe;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.boardgame.BoardGame;
import pl.coderslab.boardgame.BoardGameService;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static pl.coderslab.Role.ROLE_ADMIN;
import static pl.coderslab.ValidationUtils.validationMessage;

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
    public ResponseEntity<String> postCafe(@RequestBody Cafe cafe, HttpSession session) {
        if (session.getAttribute("role") != null && session.getAttribute("role").equals(ROLE_ADMIN)) {
            Set<ConstraintViolation<Cafe>> constraintViolations = validator.validate(cafe);
            if (constraintViolations.isEmpty()) {
                cafeService.createCafe(cafe);
                return ResponseEntity.ok("Cafe created");
            } else {
                return ResponseEntity.status(400).body(validationMessage(constraintViolations));
            }
        } else {
            return ResponseEntity.status(403).body("Forbidden");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CafeDTO> getCafe(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(convertCafeToDTO(cafeService.readCafeById(id)));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("")
    public ResponseEntity<String> putCafe(@RequestBody Cafe cafe, HttpSession session) {
        if (session.getAttribute("role") != null && session.getAttribute("role").equals(ROLE_ADMIN)) {
            Set<ConstraintViolation<Cafe>> constraintViolations = validator.validate(cafe);
            if (constraintViolations.isEmpty()) {
                cafeService.updateCafe(cafe);
                return ResponseEntity.ok("Cafe updated");
            } else {
                return ResponseEntity.status(400).body(validationMessage(constraintViolations));
            }
        } else {
            return ResponseEntity.status(403).body("Forbidden");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCafe(@PathVariable("id") Long id, HttpSession session) {
        if (session.getAttribute("role") != null && session.getAttribute("role").equals(ROLE_ADMIN)) {
            cafeService.deleteCafeById(id);
            return ResponseEntity.ok("Cafe deleted");
        } else {
            return ResponseEntity.status(403).body("Forbidden");
        }
    }

    @GetMapping("")
    public ResponseEntity<List<CafeDTO>> getAllCafes() {
        try {
            return ResponseEntity.ok(cafeService.readAllCafes().stream().map(this::convertCafeToDTO)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    // find
    @GetMapping("/find-name/{name}")
    public ResponseEntity<List<CafeDTO>> getCafesByName(@PathVariable("name") String name) {
        try {
            return ResponseEntity.ok(cafeService.findCafesByName(name).stream().map(this::convertCafeToDTO)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/find-boardGameId/{boardGameId}")
    public ResponseEntity<List<CafeDTO>> getCafesByBoardGameId(@PathVariable("boardGameId") Long boardGameId) {
        try {
            return ResponseEntity.ok(cafeService.findCafesByBoardGameId(boardGameId).stream().map(this::convertCafeToDTO)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/find-boardGameTitle-publisherName/{boardGameTitle}/{publisherName}")
    public ResponseEntity<List<CafeDTO>> getCafesByBoardGameTitleAndPublisherName(
            @PathVariable("boardGameTitle") String boardGameTitle,
            @PathVariable("publisherName") String publisherName) {
        try {
            return ResponseEntity.ok(cafeService.findCafesByBoardGameTitleAndPublisherName(boardGameTitle, publisherName)
                    .stream().map(this::convertCafeToDTO).collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/find-time/{time}")
    public ResponseEntity<List<CafeDTO>> getCafesByTime(@PathVariable("time") LocalTime time) {
        try {
            return ResponseEntity.ok(cafeService.findCafesByTimeBetweenOpeningAndClosingTime(time).stream()
                    .map(this::convertCafeToDTO).collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/find-address/{address}")
    public ResponseEntity<List<CafeDTO>> getCafesByAddressContaining(@PathVariable("address") String address) {
        try {
            return ResponseEntity.ok(cafeService.findCafesByAddressContaining(address).stream()
                    .map(this::convertCafeToDTO).collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    // update
    @PutMapping("/update/{id}/openingTime")
    public ResponseEntity<String> putCafeOpeningTime(@RequestBody LocalTime openingTime, @PathVariable("id") Long id,
                                                     HttpSession session) {
        if (session.getAttribute("role") != null && session.getAttribute("role").equals(ROLE_ADMIN)) {
            Cafe cafe = cafeService.readCafeById(id);
            cafe.setOpeningTime(openingTime);
            Set<ConstraintViolation<Cafe>> constraintViolations = validator.validate(cafe);
            if (constraintViolations.isEmpty()) {
                cafeService.updateCafeOpeningTime(openingTime, id);
                return ResponseEntity.ok("Cafe updated");
            } else {
                return ResponseEntity.status(400).body(validationMessage(constraintViolations));
            }
        } else {
            return ResponseEntity.status(403).body("Forbidden");
        }
    }

    @PutMapping("/update/{id}/closingTime")
    public ResponseEntity<String> putCafeClosingTime(@RequestBody LocalTime closingTime, @PathVariable("id") Long id,
                                                     HttpSession session) {
        if (session.getAttribute("role") != null && session.getAttribute("role").equals(ROLE_ADMIN)) {
            Cafe cafe = cafeService.readCafeById(id);
            cafe.setClosingTime(closingTime);
            Set<ConstraintViolation<Cafe>> constraintViolations = validator.validate(cafe);
            if (constraintViolations.isEmpty()) {
                cafeService.updateCafeClosingTime(closingTime, id);
                return ResponseEntity.ok("Cafe updated");
            } else {
                return ResponseEntity.status(400).body(validationMessage(constraintViolations));
            }
        } else {
            return ResponseEntity.status(403).body("Forbidden");
        }
    }

    @PutMapping("/update/{id}/addBoardGame")
    public ResponseEntity<String> putCafeBoardGamesAdd(@RequestBody Long boardGameId, @PathVariable("id") Long id,
                                                       HttpSession session) {
        if (session.getAttribute("role") != null && session.getAttribute("role").equals(ROLE_ADMIN)) {
            BoardGame boardGame = boardGameService.readBoardGameById(boardGameId);
            cafeService.updateCafeBoardGamesAdd(boardGame, id);
            return ResponseEntity.ok("Cafe updated");
        } else {
            return ResponseEntity.status(403).body("Forbidden");
        }
    }

    @PutMapping("/update/{id}/removeBoardGame")
    public ResponseEntity<String> putCafeBoardGamesRemove(@RequestBody Long boardGameId, @PathVariable("id") Long id,
                                                          HttpSession session) {
        if (session.getAttribute("role") != null && session.getAttribute("role").equals(ROLE_ADMIN)) {
            BoardGame boardGame = boardGameService.readBoardGameById(boardGameId);
            cafeService.updateCafeBoardGamesRemove(boardGame, id);
            return ResponseEntity.ok("Cafe updated");
        } else {
            return ResponseEntity.status(403).body("Forbidden");
        }
    }
}