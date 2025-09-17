package pl.coderslab.play;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.user.User;
import pl.coderslab.user.UserService;
import java.util.List;
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
        return convertPlayToDTO(playService.readPlayById(id));
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
        return playService.readAllPlays().stream().map(this::convertPlayToDTO).collect(Collectors.toList());
    }

    // find
    @GetMapping("/find-userId/{userId}")
    public List<PlayDTO> getPlaysByUserId(@PathVariable("userId") Long userId) {
        return playService.findPlaysByUserId(userId).stream().map(this::convertPlayToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/open/find-cafeId/{cafeId}")
    public List<PlayDTO> getOpenPlaysByCafeId(@PathVariable("cafeId") Long cafeId) {
        return playService.findOpenPlaysByCafeId(cafeId).stream().map(this::convertPlayToDTO)
                .collect(Collectors.toList());
    }


    @GetMapping("/open/find-boardGameId/{boardGameId}")
    public List<PlayDTO> getOpenPlaysByBoardGameId(@PathVariable("boardGameId") Long boardGameId) {
        return playService.findOpenPlaysByBoardGameId(boardGameId).stream().map(this::convertPlayToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/open/find-boardGameTitle-publisherName/{boardGameTitle}/{publisherName}")
    public List<PlayDTO> getOpenPlaysByBoardGameTitleAndPublisherName(
            @PathVariable("boardGameTitle") String boardGameTitle,
            @PathVariable("publisherName") String publisherName) {
        return playService.findOpenPlaysByBoardGameTitleAndPublisherName(boardGameTitle, publisherName)
                .stream().map(this::convertPlayToDTO).collect(Collectors.toList());
    }

    @GetMapping("/open/find-freePlaces/{freePlaces}")
    public List<PlayDTO> getOpenPlaysByFreePlacesGreaterEqual(@PathVariable("freePlaces") Integer freePlaces) {
        return playService.findOpenPlaysByFreePlacesGreaterThanEqual(freePlaces)
                .stream().map(this::convertPlayToDTO).collect(Collectors.toList());
    }

    @GetMapping("/open")
    public List<PlayDTO> getOpenPlays() {
        return playService.findOpenPlays().stream().map(this::convertPlayToDTO).collect(Collectors.toList());
    }

    // update
    @PutMapping("/update-status")
    public void putPlayStatusToPast() {
        playService.updatePlayStatusToPast();
    }

    @PutMapping("/update/{id}/addUser")
    public void putPlayUsersAdd(@RequestBody Long userId, @PathVariable("id") Long id) {
        User user = userService.readUserById(userId);
        playService.updatePlayUsersAdd(user, id);
    }

    @PutMapping("/update/{id}/removeUser")
    public void putPlayUsersRemove(@RequestBody Long userId, @PathVariable("id") Long id) {
        User user = userService.readUserById(userId);
        playService.updatePlayUsersRemove(user, id);
    }
}
