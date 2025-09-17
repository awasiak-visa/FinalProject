package pl.coderslab.user;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.Role;
import pl.coderslab.boardgame.BoardGame;
import pl.coderslab.boardgame.BoardGameService;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final BoardGameService boardGameService;
    private final Validator validator;

    public UserController(UserService userService, BoardGameService boardGameService, Validator validator) {
        this.userService = userService;
        this.boardGameService = boardGameService;
        this.validator = validator;
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
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        if (constraintViolations.isEmpty()) {
            userService.createUser(user);
        } else {
            throw new ConstraintViolationException(constraintViolations);
        }
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
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        if (constraintViolations.isEmpty()) {
            userService.updateUser(user);
        } else {
            throw new ConstraintViolationException(constraintViolations);
        }
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

    @GetMapping("/role-user")
    public List<UserDTO> getUsersByRoleUser() {
        if (userService.findUsersByRole(Role.USER).isPresent()) {
            return userService.findUsersByRole(Role.USER).get().stream().map(this::convertUserToDTO)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("No user found.");
        }
    }

    @GetMapping("/role-admin")
    public List<UserDTO> getUsersByRoleAdmin() {
        if (userService.findUsersByRole(Role.ADMIN).isPresent()) {
            return userService.findUsersByRole(Role.ADMIN).get().stream().map(this::convertUserToDTO)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("No user found.");
        }
    }

    // update
    @PutMapping("/update/{id}/username")
    public void putUserUsername(@RequestBody String username, @PathVariable("id") Long id) {
        if (userService.readUserById(id).isEmpty()) {
            throw new RuntimeException("No user found.");
        }
        User user = new User();
        user.setUsername(username);
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        if (constraintViolations.isEmpty()) {
            userService.updateUserUsername(username, id);
        } else {
            throw new ConstraintViolationException(constraintViolations);
        }
    }

    @PutMapping("/update/{id}/password")
    public void putUserPassword(@RequestBody String password, @PathVariable("id") Long id) {
        if (userService.readUserById(id).isEmpty()) {
            throw new RuntimeException("No user found.");
        }
        User user = new User();
        user.setUsername(password);
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        if (constraintViolations.isEmpty()) {
            userService.updateUserPassword(password, id);
        } else {
            throw new ConstraintViolationException(constraintViolations);
        }
    }

    @PutMapping("/update/{id}/addFavouriteGame")
    public void putUserFavouriteGamesAdd(@RequestBody Long boardGameId, @PathVariable("id") Long id) {
        if (userService.readUserById(id).isPresent() && boardGameService.readBoardGameById(boardGameId).isPresent()) {
            BoardGame boardGame = boardGameService.readBoardGameById(boardGameId).get();
            if (!userService.readUserById(id).get().getFavouriteGames().contains(boardGame)) {
                userService.updateUserFavouriteGamesAdd(boardGame, id);
            } else {
                throw new RuntimeException("Board game already on favourite list.");
            }
        } else {
            throw new RuntimeException("No user or board game found.");
        }
    }

    @PutMapping("/update/{id}/removeFavouriteGame")
    public void putUserFavouriteGamesRemove(@RequestBody Long boardGameId, @PathVariable("id") Long id) {
        if (userService.readUserById(id).isEmpty()) {
            throw new RuntimeException("No user found.");
        }
        Optional<BoardGame> boardGame = boardGameService.readBoardGameById(boardGameId);
        boardGame.ifPresent(game -> userService.updateUserFavouriteGamesRemove(game, id));
    }

    @PutMapping("/update/{id}/addWantedGame")
    public void putUserWantedGamesAdd(@RequestBody Long boardGameId, @PathVariable("id") Long id) {
        if (userService.readUserById(id).isPresent() && boardGameService.readBoardGameById(boardGameId).isPresent()) {
            BoardGame boardGame = boardGameService.readBoardGameById(boardGameId).get();
            if (!userService.readUserById(id).get().getWantedGames().contains(boardGame)) {
                userService.updateUserWantedGamesAdd(boardGame, id);
            } else {
                throw new RuntimeException("Board game already on wanted list.");
            }
        } else {
            throw new RuntimeException("No user or board game found.");
        }
    }

    @PutMapping("/update/{id}/removeWantedGame")
    public void putUserWantedGamesRemove(@RequestBody Long boardGameId, @PathVariable("id") Long id) {
        if (userService.readUserById(id).isEmpty()) {
            throw new RuntimeException("No user found.");
        }
        Optional<BoardGame> boardGame = boardGameService.readBoardGameById(boardGameId);
        boardGame.ifPresent(game -> userService.updateUserWantedGamesRemove(game, id));
    }
}
