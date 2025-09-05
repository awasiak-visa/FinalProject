package pl.coderslab.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.coderslab.Role;
import pl.coderslab.boardgame.BoardGame;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    private String password;
    private Role role;
    @ManyToMany
    @JoinTable(name = "users_favorite")
    private List<BoardGame> favouriteGames;
    @ManyToMany
    @JoinTable(name = "users_wanted")
    private List<BoardGame> wantedGames;
}
