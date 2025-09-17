package pl.coderslab.play;

import org.springframework.web.bind.annotation.*;
import pl.coderslab.Status;
import pl.coderslab.user.User;
import pl.coderslab.user.UserService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/plays")
public class PlayController {

    private final PlayService playService;
    private final UserService userService;

    public PlayController(PlayService playService, UserService userService) {
        this.playService = playService;
        this.userService = userService;
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
        playService.createPlay(play);
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
        playService.updatePlay(play);
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
    public void putPlayStatus(@RequestBody Map<String, Object> params) {
        playService.updatePlayStatus((Status) params.get("status"), (Long) params.get("id"));
    }

    @PutMapping("/update-addUser")
    public void putPlayUsersAdd(@RequestBody Map<String, Object> params) {
        Optional<User> user = userService.readUserById((Long) params.get("userId"));
        user.ifPresent(playUser -> playService
                .updatePlayUsersAdd(playUser, (Long) params.get("id")));
    }

    @PutMapping("/update-removeUser")
    public void putPlayUsersRemove(@RequestBody Map<String, Object> params) {
        Optional<User> user = userService.readUserById((Long) params.get("userId"));
        user.ifPresent(playUser -> playService
                .updatePlayUsersRemove(playUser, (Long) params.get("id")));
    }

    @PutMapping("/update-freePlaces")
    public void putPlayFreePlaces(@RequestBody Map<String, Object> params) {
        playService.updatePlayFreePlaces((Integer) params.get("freePlaces"), (Long) params.get("id"));
    }
}
