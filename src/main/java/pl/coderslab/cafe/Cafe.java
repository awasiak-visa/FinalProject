package pl.coderslab.cafe;

import jakarta.persistence.*;
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
    private String name;
    private LocalTime openingTime;
    private LocalTime closingTime;
    @Column(unique = true)
    private String address;
    @ManyToMany
    @JoinTable(name = "cafes_boardgames")
    private List<BoardGame> boardGames;
}
