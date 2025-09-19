package pl.coderslab.user;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.Role;
import pl.coderslab.boardgame.BoardGame;
import pl.coderslab.boardgame.BoardGameService;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static pl.coderslab.Role.ROLE_ADMIN;
import static pl.coderslab.ValidationUtils.validationMessage;

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
    public ResponseEntity<String> postUser(@RequestBody User user, HttpSession session) {
        if (session.getAttribute("userId") == null ||
                (session.getAttribute("role") != null && session.getAttribute("role").equals(ROLE_ADMIN))) {
            Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
            if (constraintViolations.isEmpty()) {
                if (!session.getAttribute("role").equals(ROLE_ADMIN)) {
                    user.setRole(Role.ROLE_USER);
                }
                userService.createUser(user);
                return ResponseEntity.ok("User created");
            } else {
                return ResponseEntity.status(400).body(validationMessage(constraintViolations));
            }
        } else {
            return ResponseEntity.status(403).body("Forbidden");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(convertUserToDTO(userService.readUserById(id)));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("")
    public ResponseEntity<String> putUser(@RequestBody User user, HttpSession session) {
        if (session.getAttribute("userId") != null && session.getAttribute("userId").equals(user.getId())) {
            Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
            if (constraintViolations.isEmpty()) {
                if (!session.getAttribute("role").equals(ROLE_ADMIN)) {
                    user.setRole(Role.ROLE_USER);
                }
                userService.updateUser(user);
                return ResponseEntity.ok("User updated");
            } else {
                return ResponseEntity.status(400).body(validationMessage(constraintViolations));
            }
        } else {
            return ResponseEntity.status(403).body("Forbidden");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id, HttpSession session) {
        if ((session.getAttribute("userId") != null && session.getAttribute("userId").equals(id))
                || (session.getAttribute("role") != null && session.getAttribute("role").equals(ROLE_ADMIN))) {
            userService.deleteUserById(id);
            session.invalidate();
            return ResponseEntity.ok("User deleted");
        } else {
            return ResponseEntity.status(403).body("Forbidden");
        }
    }

    @GetMapping("")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        try {
            return ResponseEntity.ok(userService.readAllUsers().stream().map(this::convertUserToDTO)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    // find
    @GetMapping("/find-username/{username}")
    public ResponseEntity<List<UserDTO>> getUsersByUsernameContaining(@PathVariable("username") String username) {
        try {
            return ResponseEntity.ok(userService.findUsersByUsernameContaining(username).stream()
                    .map(this::convertUserToDTO).collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/find-favouriteGameId/{favouriteGameId}")
    public ResponseEntity<List<UserDTO>> getUsersByFavouriteGameId(
            @PathVariable("favouriteGameId") Long favouriteGameId) {
        try {
            return ResponseEntity.ok(userService.findUsersByFavouriteGameId(favouriteGameId).stream().map(this::convertUserToDTO)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/role-user")
    public ResponseEntity<List<UserDTO>> getUsersByRoleUser() {
        try {
            return ResponseEntity.ok(userService.findUsersByRole(Role.ROLE_USER).stream().map(this::convertUserToDTO)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/role-admin")
    public ResponseEntity<List<UserDTO>> getUsersByRoleAdmin() {
        try {
            return ResponseEntity.ok(userService.findUsersByRole(ROLE_ADMIN).stream().map(this::convertUserToDTO)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    // update
    @PutMapping("/update/{id}/username")
    public ResponseEntity<String> putUserUsername(@RequestBody String username, @PathVariable("id") Long id, HttpSession session) {
        if (session.getAttribute("userId") != null && session.getAttribute("userId").equals(id)) {
            User user = userService.readUserById(id);
            user.setUsername(username);
            Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
            if (constraintViolations.isEmpty()) {
                userService.updateUserUsername(username, id);
                return ResponseEntity.ok("User updated");
            } else {
                return ResponseEntity.status(400).body(validationMessage(constraintViolations));
            }
        } else {
            return ResponseEntity.status(403).body("Forbidden");
        }
    }

    @PutMapping("/update/{id}/password")
    public ResponseEntity<String> putUserPassword(@RequestBody String password, @PathVariable("id") Long id, HttpSession session) {
        if (session.getAttribute("userId") != null && session.getAttribute("userId").equals(id)) {
            User user = userService.readUserById(id);
            user.setUsername(password);
            Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
            if (constraintViolations.isEmpty()) {
                userService.updateUserPassword(password, id);
                return ResponseEntity.ok("User updated");
            } else {
                return ResponseEntity.status(400).body(validationMessage(constraintViolations));
            }
        } else {
            return ResponseEntity.status(403).body("Forbidden");
        }
    }

    @PutMapping("/update/{id}/addFavouriteGame")
    public ResponseEntity<String> putUserFavouriteGamesAdd(@RequestBody Long boardGameId, @PathVariable("id") Long id,
                                                           HttpSession session) {
        if (session.getAttribute("userId") != null && session.getAttribute("userId").equals(id)) {
            BoardGame boardGame = boardGameService.readBoardGameById(boardGameId);
            userService.updateUserFavouriteGamesAdd(boardGame, id);
            return ResponseEntity.ok("User updated");
        } else {
            return ResponseEntity.status(403).body("Forbidden");
        }
    }

    @PutMapping("/update/{id}/removeFavouriteGame")
    public ResponseEntity<String> putUserFavouriteGamesRemove(@RequestBody Long boardGameId, @PathVariable("id") Long id,
                                                              HttpSession session) {
        if (session.getAttribute("userId") != null && session.getAttribute("userId").equals(id)) {
            BoardGame boardGame = boardGameService.readBoardGameById(boardGameId);
            userService.updateUserFavouriteGamesRemove(boardGame, id);
            return ResponseEntity.ok("User updated");
        } else {
            return ResponseEntity.status(403).body("Forbidden");
        }
    }

    @PutMapping("/update/{id}/addWantedGame")
    public ResponseEntity<String> putUserWantedGamesAdd(@RequestBody Long boardGameId, @PathVariable("id") Long id, HttpSession session) {
        if (session.getAttribute("userId") != null && session.getAttribute("userId").equals(id)) {
            BoardGame boardGame = boardGameService.readBoardGameById(boardGameId);
            userService.updateUserWantedGamesAdd(boardGame, id);
            return ResponseEntity.ok("User updated");
        } else {
            return ResponseEntity.status(403).body("Forbidden");
        }
    }

    @PutMapping("/update/{id}/removeWantedGame")
    public ResponseEntity<String> putUserWantedGamesRemove(@RequestBody Long boardGameId, @PathVariable("id") Long id,
                                                           HttpSession session) {
        if (session.getAttribute("userId") != null && session.getAttribute("userId").equals(id)) {
            BoardGame boardGame = boardGameService.readBoardGameById(boardGameId);
            userService.updateUserWantedGamesRemove(boardGame, id);
            return ResponseEntity.ok("User updated");
        } else {
            return ResponseEntity.status(403).body("Forbidden");
        }
    }
}
