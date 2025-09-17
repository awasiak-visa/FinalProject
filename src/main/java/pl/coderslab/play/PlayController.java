package pl.coderslab.play;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.user.User;
import pl.coderslab.user.UserService;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    public void postPlay(@RequestBody Play play) {
        Set<ConstraintViolation<Play>> constraintViolations = validator.validate(play);
        if (constraintViolations.isEmpty()) {
            playService.createPlay(play);
        } else {
            throw new ConstraintViolationException(constraintViolations);
        }
    }

    @GetMapping("/{id}")
    public PlayDTO getPlay(@PathVariable("id") Long id) {
        if (playService.readPlayById(id).isPresent()) {
            return convertPlayToDTO(playService.readPlayById(id).get());
        } else {
            throw new RuntimeException("No play found.");
        }
    }

    @PutMapping("")
    public void putPlay(@RequestBody Play play) {
        Set<ConstraintViolation<Play>> constraintViolations = validator.validate(play);
        if (constraintViolations.isEmpty()) {
            playService.updatePlay(play);
        } else {
            throw new ConstraintViolationException(constraintViolations);
        }
    }

    @DeleteMapping("/{id}")
    public void deletePlay(@PathVariable("id") Long id) {
        playService.deletePlayById(id);
    }

    @GetMapping("")
    public List<PlayDTO> getAllPlays() {
        if (!playService.readAllPlays().isEmpty()) {
            return playService.readAllPlays().stream().map(this::convertPlayToDTO).collect(Collectors.toList());
        } else {
            throw new RuntimeException("No review found.");
        }
    }

    // find
    @GetMapping("/find-userId/{userId}")
    public List<PlayDTO> getPlaysByUserId(@PathVariable("userId") Long userId) {
        if (playService.findPlaysByUserId(userId).isPresent()) {
            return playService.findPlaysByUserId(userId).get().stream().map(this::convertPlayToDTO)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("No play found.");
        }
    }

    @GetMapping("/open/find-cafeId/{cafeId}")
    public List<PlayDTO> getOpenPlaysByCafeId(@PathVariable("cafeId") Long cafeId) {
        if (playService.findOpenPlaysByCafeId(cafeId).isPresent()) {
            return playService.findOpenPlaysByCafeId(cafeId).get().stream().map(this::convertPlayToDTO)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("No play found.");
        }
    }

    @GetMapping("/open/find-boardGameId/{boardGameId}")
    public List<PlayDTO> getOpenPlaysByBoardGameId(@PathVariable("boardGameId") Long boardGameId) {
        if (playService.findOpenPlaysByBoardGameId(boardGameId).isPresent()) {
            return playService.findOpenPlaysByBoardGameId(boardGameId).get().stream().map(this::convertPlayToDTO)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("No play found.");
        }
    }

    @GetMapping("/open/find-boardGameTitle-publisherName/{boardGameTitle}/{publisherName}")
    public List<PlayDTO> getOpenPlaysByBoardGameTitleAndPublisherName(
            @PathVariable("boardGameTitle") String boardGameTitle,
            @PathVariable("publisherName") String publisherName) {
        if (playService.findOpenPlaysByBoardGameTitleAndPublisherName(boardGameTitle, publisherName).isPresent()) {
            return playService.findOpenPlaysByBoardGameTitleAndPublisherName(boardGameTitle, publisherName)
                    .get().stream().map(this::convertPlayToDTO).collect(Collectors.toList());
        } else {
            throw new RuntimeException("No play found.");
        }
    }

    @GetMapping("/open/find-freePlaces/{freePlaces}")
    public List<PlayDTO> getOpenPlaysByFreePlacesGreaterEqual(@PathVariable("freePlaces") Integer freePlaces) {
        if (playService.findOpenPlaysByFreePlacesGreaterThanEqual(freePlaces).isPresent()) {
            return playService.findOpenPlaysByFreePlacesGreaterThanEqual(freePlaces)
                    .get().stream().map(this::convertPlayToDTO).collect(Collectors.toList());
        } else {
            throw new RuntimeException("No play found.");
        }
    }

    @GetMapping("/open")
    public List<PlayDTO> getOpenPlays() {
        if (playService.findOpenPlays().isPresent()) {
            return playService.findOpenPlays().get().stream().map(this::convertPlayToDTO)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("No play found.");
        }
    }

    // update
    @PutMapping("/update-status")
    public void putPlayStatusToPast() {
        playService.updatePlayStatusToPast();
    }

    @PutMapping("/update/{id}/addUser")
    public void putPlayUsersAdd(@RequestBody Long userId, @PathVariable("id") Long id) {
        if (playService.readPlayById(id).isPresent() && userService.readUserById(userId).isPresent()) {
            User user = userService.readUserById(userId).get();
            if (!playService.readPlayById(id).get().getUsers().contains(user)) {
                playService.updatePlayUsersAdd(user, id);
            } else {
                throw new RuntimeException("User already added to the play.");
            }
        } else {
            throw new RuntimeException("No play or user found.");
        }
    }

    @PutMapping("/update/{id}/removeUser")
    public void putPlayUsersRemove(@RequestBody Long userId, @PathVariable("id") Long id) {
        if (playService.readPlayById(id).isEmpty()) {
            throw new RuntimeException("No play found.");
        }
        Optional<User> user = userService.readUserById(userId);
        user.ifPresent(playUser -> playService
                .updatePlayUsersRemove(playUser, id));
    }
}
