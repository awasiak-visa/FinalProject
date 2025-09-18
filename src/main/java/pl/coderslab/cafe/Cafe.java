package pl.coderslab.cafe;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import pl.coderslab.boardgame.BoardGame;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "cafes")
public class Cafe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 2, max = 30)
    @NotNull
    private String name;
    @NotNull
    private LocalTime openingTime;
    @NotNull
    private LocalTime closingTime;
    @Column(unique = true)
    @Size(min = 4, max = 50)
    @NotNull
    private String address;
    @ManyToMany
    @JoinTable(name = "cafes_boardgames")
    private List<BoardGame> boardGames;
}
