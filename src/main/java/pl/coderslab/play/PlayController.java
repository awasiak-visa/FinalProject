package pl.coderslab.play;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.user.User;
import pl.coderslab.user.UserService;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static pl.coderslab.ValidationUtils.validationMessage;

@RestController
@RequestMapping("/plays")
public class PlayController {

    private final PlayService playService;
    private final UserService userService;
    private final Validator validator;

    public PlayController(PlayService playService, UserService userService, Validator validator) {
        this.playService = playService;
        this.userService = userService;
        this.validator = validator;
    }

    private PlayDTO convertPlayToDTO(Play play) {
        return PlayDTO.builder().id(play.getId())
                .boardGameInfo(List.of(play.getBoardGame().getTitle(), play.getBoardGame().getPublisher().getName()))
                .dateTime(play.getDateTime())
                .cafeInfo(List.of(play.getCafe().getName(), play.getCafe().getAddress()))
                .usernames(play.getUsers().stream().map(User::getUsername).collect(Collectors.toList()))
                .status(play.getStatus()).freePlaces(play.getFreePlaces())
                .build();
    }

    @PostMapping("")
    public ResponseEntity<String> postPlay(@RequestBody Play play, HttpSession session) {
        if (session.getAttribute("userId") != null) {
            Set<ConstraintViolation<Play>> constraintViolations = validator.validate(play);
            if (constraintViolations.isEmpty()) {
                play.setUsers(List.of(userService.readUserById((Long) session.getAttribute("userId"))));
                playService.createPlay(play);
                return ResponseEntity.ok("Play created");
            } else {
                return ResponseEntity.status(400).body(validationMessage(constraintViolations));
            }
        } else {
            return ResponseEntity.status(403).body("Forbidden");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayDTO> getPlay(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(convertPlayToDTO(playService.readPlayById(id)));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("")
    public ResponseEntity<String> putPlay(@RequestBody Play play, HttpSession session) {
        if (session.getAttribute("role").equals("ROLE_ADMIN")) {
            Set<ConstraintViolation<Play>> constraintViolations = validator.validate(play);
            if (constraintViolations.isEmpty()) {
                playService.updatePlay(play);
                return ResponseEntity.ok("Play updated");
            } else {
                return ResponseEntity.status(400).body(validationMessage(constraintViolations));
            }
        } else {
            return ResponseEntity.status(403).body("Forbidden");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlay(@PathVariable("id") Long id, HttpSession session) {
        if (session.getAttribute("role").equals("ROLE_ADMIN")) {
            playService.deletePlayById(id);
            return ResponseEntity.ok("Play deleted");
        } else {
            return ResponseEntity.status(403).body("Forbidden");
        }
    }

    @GetMapping("")
    public ResponseEntity<List<PlayDTO>> getAllPlays() {
        try {
            return ResponseEntity.ok(playService.readAllPlays().stream().map(this::convertPlayToDTO)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    // find
    @GetMapping("/find-userId/{userId}")
    public ResponseEntity<List<PlayDTO>> getPlaysByUserId(@PathVariable("userId") Long userId) {
        try {
            return ResponseEntity.ok(playService.findPlaysByUserId(userId).stream().map(this::convertPlayToDTO)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/open/find-cafeId/{cafeId}")
    public ResponseEntity<List<PlayDTO>> getOpenPlaysByCafeId(@PathVariable("cafeId") Long cafeId) {
        try {
            return ResponseEntity.ok(playService.findOpenPlaysByCafeId(cafeId).stream().map(this::convertPlayToDTO)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/open/find-boardGameId/{boardGameId}")
    public ResponseEntity<List<PlayDTO>> getOpenPlaysByBoardGameId(@PathVariable("boardGameId") Long boardGameId) {
        try {
            return ResponseEntity.ok(playService.findOpenPlaysByBoardGameId(boardGameId).stream()
                    .map(this::convertPlayToDTO).collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/open/find-boardGameTitle-publisherName/{boardGameTitle}/{publisherName}")
    public ResponseEntity<List<PlayDTO>> getOpenPlaysByBoardGameTitleAndPublisherName(
            @PathVariable("boardGameTitle") String boardGameTitle,
            @PathVariable("publisherName") String publisherName) {
        try {
            return ResponseEntity.ok(playService.findOpenPlaysByBoardGameTitleAndPublisherName(boardGameTitle, publisherName)
                    .stream().map(this::convertPlayToDTO).collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/open/find-freePlaces/{freePlaces}")
    public ResponseEntity<List<PlayDTO>> getOpenPlaysByFreePlacesGreaterEqual(
            @PathVariable("freePlaces") Integer freePlaces) {
        try {
            return ResponseEntity.ok(playService.findOpenPlaysByFreePlacesGreaterThanEqual(freePlaces)
                    .stream().map(this::convertPlayToDTO).collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/open")
    public ResponseEntity<List<PlayDTO>> getOpenPlays() {
        try {
            return ResponseEntity.ok(playService.findOpenPlays().stream().map(this::convertPlayToDTO)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    // update
    @PutMapping("/update-status")
    public ResponseEntity<String> putPlayStatusToPast() {
        try {
            playService.updatePlayStatusToPast();
            return ResponseEntity.ok("Statuses updated");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error");
        }
    }

    @PutMapping("/update/{id}/addUser")
    public ResponseEntity<String> putPlayUsersAdd(@RequestBody Long userId, @PathVariable("id") Long id,
                                                  HttpSession session) {
        if (session.getAttribute("userId").equals(userId)) {
            User user = userService.readUserById(userId);
            playService.updatePlayUsersAdd(user, id);
            return ResponseEntity.ok("Play updated");
        } else {
            return ResponseEntity.status(403).body("Forbidden");
        }
    }

    @PutMapping("/update/{id}/removeUser")
    public ResponseEntity<String> putPlayUsersRemove(@RequestBody Long userId, @PathVariable("id") Long id,
                                                     HttpSession session) {
        if (session.getAttribute("userId").equals(userId)) {
            User user = userService.readUserById(userId);
            playService.updatePlayUsersRemove(user, id);
            return ResponseEntity.ok("Play updated");
        } else {
            return ResponseEntity.status(403).body("Forbidden");
        }
    }
}
