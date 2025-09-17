package pl.coderslab.user;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.Role;
import pl.coderslab.boardgame.BoardGame;
import pl.coderslab.boardgame.BoardGameService;
import java.util.List;
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
        return convertUserToDTO(userService.readUserById(id));
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
        return userService.readAllUsers().stream().map(this::convertUserToDTO).collect(Collectors.toList());
    }

    // find
    @GetMapping("/find-username/{username}")
    public List<UserDTO> getUsersByUsernameContaining(@PathVariable("username") String username) {
        return userService.findUsersByUsernameContaining(username).stream().map(this::convertUserToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/find-favouriteGameId/{favouriteGameId}")
    public List<UserDTO> getUsersByFavouriteGameId(@PathVariable("favouriteGameId") Long favouriteGameId) {
        return userService.findUsersByFavouriteGameId(favouriteGameId).stream().map(this::convertUserToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/role-user")
    public List<UserDTO> getUsersByRoleUser() {
        return userService.findUsersByRole(Role.ROLE_USER).stream().map(this::convertUserToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/role-admin")
    public List<UserDTO> getUsersByRoleAdmin() {
        return userService.findUsersByRole(Role.ROLE_ADMIN).stream().map(this::convertUserToDTO)
                .collect(Collectors.toList());
    }

    // update
    @PutMapping("/update/{id}/username")
    public void putUserUsername(@RequestBody String username, @PathVariable("id") Long id) {
        User user = userService.readUserById(id);
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
        User user = userService.readUserById(id);
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
        BoardGame boardGame = boardGameService.readBoardGameById(boardGameId);
        userService.updateUserFavouriteGamesAdd(boardGame, id);
    }

    @PutMapping("/update/{id}/removeFavouriteGame")
    public void putUserFavouriteGamesRemove(@RequestBody Long boardGameId, @PathVariable("id") Long id) {
        BoardGame boardGame = boardGameService.readBoardGameById(boardGameId);
        userService.updateUserFavouriteGamesRemove(boardGame, id);
    }

    @PutMapping("/update/{id}/addWantedGame")
    public void putUserWantedGamesAdd(@RequestBody Long boardGameId, @PathVariable("id") Long id) {
        BoardGame boardGame = boardGameService.readBoardGameById(boardGameId);
        userService.updateUserWantedGamesAdd(boardGame, id);
    }

    @PutMapping("/update/{id}/removeWantedGame")
    public void putUserWantedGamesRemove(@RequestBody Long boardGameId, @PathVariable("id") Long id) {
        BoardGame boardGame = boardGameService.readBoardGameById(boardGameId);
        userService.updateUserWantedGamesRemove(boardGame, id);
    }
}
