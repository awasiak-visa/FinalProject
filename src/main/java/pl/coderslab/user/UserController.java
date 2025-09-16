package pl.coderslab.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
}
