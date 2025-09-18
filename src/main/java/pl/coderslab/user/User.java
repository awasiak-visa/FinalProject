package pl.coderslab.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;
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
    @NotNull
    @Size(min = 5, max = 20)
    private String username;
    @Column(unique = true)
    @Email
    @NotNull
    private String email;
    @NotNull
    @Size(min = 8)
    private String password;
    @NotNull
    private Role role;
    @ManyToMany
    @JoinTable(name = "users_favourite")
    @UniqueElements
    private List<BoardGame> favouriteGames;
    @ManyToMany
    @JoinTable(name = "users_wanted")
    @UniqueElements
    private List<BoardGame> wantedGames;
}
