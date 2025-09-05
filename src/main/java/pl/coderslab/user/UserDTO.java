package pl.coderslab.user;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private List<List<String>> favouriteGamesInfo;
    private List<List<String>> wantedGamesInfo;
}
