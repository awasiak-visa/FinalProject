package pl.coderslab.cafe;

import org.springframework.web.bind.annotation.*;
import pl.coderslab.boardgame.BoardGame;
import pl.coderslab.boardgame.BoardGameService;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cafes")
public class CafeController {
    private final CafeService cafeService;
    private final BoardGameService boardGameService;

    public CafeController(CafeService cafeService, BoardGameService boardGameService) {
        this.cafeService = cafeService;
        this.boardGameService = boardGameService;
    }

    private CafeDTO convertCafeToDTO(Cafe cafe) {
        return CafeDTO.builder().id(cafe.getId()).name(cafe.getName())
                .openingTime(cafe.getOpeningTime()).closingTime(cafe.getClosingTime())
                .address(cafe.getAddress()).boardGames(cafe.getBoardGames())
                .build();
    }


    @PostMapping("")
    public void postCafe(@RequestBody Cafe cafe) {
        cafeService.createCafe(cafe);
    }

    @GetMapping("/{id}")
    public CafeDTO getCafeById(@PathVariable("id") Long id) {
        if (cafeService.readCafeById(id).isPresent()) {
            return convertCafeToDTO(cafeService.readCafeById(id).get());
        } else {
            throw new RuntimeException("No board game found.");
        }
    }

    @PutMapping("")
    public void putCafe(@RequestBody Cafe cafe) {
        cafeService.updateCafe(cafe);
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

    @GetMapping("/find-boardGameTitle/{boardGameTitle}/find-boardGamePublisherName/{boardGamePublisherName}")
    public List<CafeDTO> getCafesByBoardGameId(@PathVariable("boardGameTitle") String boardGameTitle,
                                               @PathVariable("boardGamePublisherName") String boardGamePublisherName) {
        if (cafeService.findCafesByBoardGamTitleAndPublisherName(boardGameTitle, boardGamePublisherName).isPresent()) {
            return cafeService.findCafesByBoardGamTitleAndPublisherName(boardGameTitle, boardGamePublisherName)
                    .get().stream().map(this::convertCafeToDTO).collect(Collectors.toList());
        } else {
            throw new RuntimeException("No cafe found.");
        }
    }

    @GetMapping("/find-openingTime/{openingTime}")
    public List<CafeDTO> getCafesByOpeningTimeBefore(@PathVariable("openingTime") LocalTime openingTime) {
        if (cafeService.findCafesByOpeningTimeBefore(openingTime).isPresent()) {
            return cafeService.findCafesByOpeningTimeBefore(openingTime).get().stream().map(this::convertCafeToDTO)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("No cafe found.");
        }
    }

    @GetMapping("/find-closingTime/{closingTime}")
    public List<CafeDTO> getCafesByClosingTimeAfter(@PathVariable("closingTime") LocalTime closingTime) {
        if (cafeService.findCafesByClosingTimeAfter(closingTime).isPresent()) {
            return cafeService.findCafesByClosingTimeAfter(closingTime).get().stream().map(this::convertCafeToDTO)
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

    @PutMapping("/update-address")
    public void putCafeAddress(@RequestBody Map<String, Object> params) {
        cafeService.updateCafeAddress((String) params.get("address"), (Long) params.get("id"));
    }

    @PutMapping("/update-name")
    public void putCafeName(@RequestBody Map<String, Object> params) {
        cafeService.updateCafeName((String) params.get("name"), (Long) params.get("id"));
    }

    @PutMapping("/update-openHours")
    public void putCafeOpenHours(@RequestBody Map<String, Object> params) {
        cafeService.updateCafeOpenHours((LocalTime) params.get("openingTime"), (LocalTime) params.get("closingTime"),
                (Long) params.get("id"));
    }

    @PutMapping("/update-addBoardGame")
    public void putCafeBoardGamesAdd(@RequestBody Map<String, Object> params) {
        Optional<BoardGame> boardGame = boardGameService.readBoardGameById((Long) params.get("boardGameId"));
        boardGame.ifPresent(game -> cafeService.updateCafeBoardGamesAdd(game, (Long) params.get("id")));
    }

    @PutMapping("/update-removeBoardGame")
    public void putCafeBoardGamesRemove(@RequestBody Map<String, Object> params) {
        Optional<BoardGame> boardGame = boardGameService.readBoardGameById((Long) params.get("boardGameId"));
        boardGame.ifPresent(game -> cafeService.updateCafeBoardGamesRemove(game, (Long) params.get("id")));
    }
}