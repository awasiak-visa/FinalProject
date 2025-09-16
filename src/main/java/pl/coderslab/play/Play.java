package pl.coderslab.play;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.coderslab.Status;
import pl.coderslab.cafe.Cafe;
import pl.coderslab.user.User;
import pl.coderslab.boardgame.BoardGame;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "plays")
public class Play {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private BoardGame boardGame;
    private LocalDateTime dateTime;
    @ManyToOne
    private Cafe cafe;
    @ManyToMany
    private List<User> users;
    private Status status;
}
