package pl.coderslab.user;

import org.springframework.web.bind.annotation.*;
import pl.coderslab.boardgame.BoardGame;
import pl.coderslab.boardgame.BoardGameService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final BoardGameService boardGameService;

    public UserController(UserService userService, BoardGameService boardGameService) {
        this.userService = userService;
        this.boardGameService = boardGameService;
    }

    private UserDTO convertUserToDTO(User user) {
        return UserDTO.builder().id(user.getId()).username(user.getUsername())
                .email(user.getEmail())
                .favouriteGamesInfo(user.getFavouriteGames().stream()
                        .map(game -> List.of(game.getTitle(), game.getPublisher().getName()))
                        .collect(Collectors.toList()))
                .wantedGamesInfo(user.getWantedGames().stream()
                        .map(game -> List.of(game.getTitle(), game.getPublisher().getName()))
                        .collect(Collectors.toList()))
                .build();
    }

    @PostMapping("")
    public void postUser(@RequestBody User user) {
        userService.createUser(user);
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable("id") Long id) {
        if (userService.readUserById(id).isPresent()) {
            return convertUserToDTO(userService.readUserById(id).get());
        } else {
            throw new RuntimeException("No user found.");
        }
    }

    @PutMapping("")
    public void putUser(@RequestBody User user) {
        userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
    }

    @GetMapping("")
    public List<UserDTO> getAllUsers() {
        if (!userService.readAllUsers().isEmpty()) {
            return userService.readAllUsers().stream().map(this::convertUserToDTO).collect(Collectors.toList());
        } else {
            throw new RuntimeException("No user found.");
        }
    }

    // find
    @GetMapping("/find-username/{username}")
    public List<UserDTO> getUsersByUsernameContaining(@PathVariable("username") String username) {
        if (userService.findUsersByUsernameContaining(username).isPresent()) {
            return userService.findUsersByUsernameContaining(username).get().stream().map(this::convertUserToDTO)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("No user found.");
        }
    }

    @GetMapping("/find-favouriteGameId/{favouriteGameId}")
    public List<UserDTO> getUsersByFavouriteGameId(@PathVariable("favouriteGameId") Long favouriteGameId) {
        if (userService.findUsersByFavouriteGameId(favouriteGameId).isPresent()) {
            return userService.findUsersByFavouriteGameId(favouriteGameId).get().stream().map(this::convertUserToDTO)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("No user found.");
        }
    }

    // update
    @PutMapping("/update-username")
    public void putUserUsername(@RequestBody Map<String, Object> params) {
        userService.updateUserUsername((String) params.get("username"), (Long) params.get("id"));
    }

    @PutMapping("/update-password")
    public void putUserPassword(@RequestBody Map<String, Object> params) {
        userService.updateUserPassword((String) params.get("password"), (Long) params.get("id"));
    }

    @PutMapping("/update-addFavouriteGame")
    public void putUserFavouriteGamesAdd(@RequestBody Map<String, Object> params) {
        Optional<BoardGame> boardGame = boardGameService.readBoardGameById((Long) params.get("boardGameId"));
        boardGame.ifPresent(game -> userService.updateUserFavouriteGamesAdd(game, (Long) params.get("id")));
    }

    @PutMapping("/update-removeFavouriteGame")
    public void putUserFavouriteGamesRemove(@RequestBody Map<String, Object> params) {
        Optional<BoardGame> boardGame = boardGameService.readBoardGameById((Long) params.get("boardGameId"));
        boardGame.ifPresent(game -> userService.updateUserFavouriteGamesRemove(game, (Long) params.get("id")));
    }

    @PutMapping("/update-addWantedGame")
    public void putUserWantedGamesAdd(@RequestBody Map<String, Object> params) {
        Optional<BoardGame> boardGame = boardGameService.readBoardGameById((Long) params.get("boardGameId"));
        boardGame.ifPresent(game -> userService.updateUserWantedGamesAdd(game, (Long) params.get("id")));
    }

    @PutMapping("/update-removeWantedGame")
    public void putUserWantedGamesRemove(@RequestBody Map<String, Object> params) {
        Optional<BoardGame> boardGame = boardGameService.readBoardGameById((Long) params.get("boardGameId"));
        boardGame.ifPresent(game -> userService.updateUserWantedGamesRemove(game, (Long) params.get("id")));
    }
}
