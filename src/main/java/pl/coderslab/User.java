package pl.coderslab;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
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
